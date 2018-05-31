package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.IOException;
import java.util.List;

/**
 * This class represents executed process and requires {@link Task} to be constructed from.
 */
final public class ShellProcess {
    private final Task task;

    private ShellProcess(Task task) {
        this.task = task;
    }

    /**
     * @param context is a execution context which is passed to task.
     * @param args are string arguments fot task's main method.
     * @return status of the executed task.
     */
    public TaskStatus execute(ProcessContext context, List<String> args) {
        TaskStatus exitCode = task.main(context, args);
        try {
            context.getStdout().close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output stream", e);
        }
        return exitCode;
    }

    /**
     * Factory method to create instance of {@link ShellProcess}
     * @param task which should be launched by this process.
     * @return process with passed task inside.
     */
    public static ShellProcess createProcess(Task task) {
        return new ShellProcess(task);
    }
}
