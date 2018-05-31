package ru.spbau.cliapp.core;

import ru.spbau.cliapp.interpreter.TasksRegistry;
import ru.spbau.cliapp.util.IOUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents pipeline created from multiple tasks, which are described by {@link TaskInfo}. After pipeline
 * is created, it could be executed given system resources (like working folder and standard streams).
 *
 * {@link TasksRegistry} is used for creating tasks by their names.
 *
 * Could be reused (does not hold any state).
 */
public class Workflow {
    private final List<TaskInfo> tasks;
    private final TasksRegistry namedTasks;

    public Workflow(TasksRegistry taskRegistry, List<TaskInfo> tasksToRun) {
        this.tasks = tasksToRun;
        this.namedTasks = taskRegistry;
    }

    /**
     * Connects tasks in workflow with pipes and invokes them sequentially.
     *
     * Does not close any of the passed streams.
     *
     * @return TaskStatus of the last executed task.
     * @throws IOException if anything wrong happens during execution.
     */
    public TaskStatus execute(Path workingDir, InputStream stdin, OutputStream stdout, OutputStream stderr) throws IOException {
        List<ShellProcess> processes = tasks.stream()
            .map(TaskInfo::getTaskName)
            .map(namedTasks::getTaskByName)
            .map(ShellProcess::new)
            .collect(Collectors.toList());

        List<Pipe> pipes = new ArrayList<>();
        for (int i = 0; i < tasks.size() - 1; i++) {
            pipes.add(new SystemPipe());
        }

        List<InputStream> inputStreams = Stream.concat(
            Stream.of(IOUtil.ignoringClose(stdin)),
            pipes.stream().map(Pipe::getInputStream)
        ).collect(Collectors.toList());

        List<OutputStream> outputStreams = Stream.concat(
            pipes.stream().map(Pipe::getOutputStream),
            Stream.of(IOUtil.ignoringClose(stdout))
        ).collect(Collectors.toList());

        TaskStatus pipelineExecutionStatus = SUCCESS.INSTANCE;

        for (int i = 0; i < processes.size(); i++) {
            ShellProcess process = processes.get(i);
            TaskInfo task = tasks.get(i);
            InputStream currentIn = inputStreams.get(i);
            OutputStream currentOut = outputStreams.get(i);
            ProcessContext context = new BasicProcessContext(workingDir, currentIn, currentOut, stderr);
            pipelineExecutionStatus = process.execute(context, task.getArguments());
        }

        return pipelineExecutionStatus;
    }
}
