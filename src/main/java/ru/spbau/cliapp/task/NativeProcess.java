package ru.spbau.cliapp.task;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.PumpStreamHandler;
import ru.spbau.cliapp.core.ERROR;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.SUCCESS;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.IOException;
import java.util.List;

/**
 * This task is a placeholder task which launches actual process by name in the OS.
 *
 * It should be used as a last resort when cli failed to find more specific {@link Task} implementation.
 */
public class NativeProcess implements Task {

    private final String taskName;

    /**
     * @param taskName is a name of program that should be launched.
     */
    public NativeProcess(String taskName) {
        this.taskName = taskName;
    }

    @Override
    public TaskStatus main(ProcessContext context, List<String> args) {
        try {
            return executeNativeProcess(context, args);
        } catch (IOException e) {
            tryToPrintError(context, e);
            return new ERROR(e);
        }
    }

    private TaskStatus executeNativeProcess(ProcessContext context, List<String> args) throws IOException {
        DefaultExecutor defaultExecutor = new DefaultExecutor();

        CommandLine commandLine = new CommandLine(taskName).addArguments(args.toArray(new String[0]));
        defaultExecutor.setStreamHandler(new PumpStreamHandler(context.getStdout(), context.getErr(), context.getStdin()));
        defaultExecutor.execute(commandLine);

        return SUCCESS.INSTANCE;
    }

    private void tryToPrintError(ProcessContext context, IOException e) {
        try {
            context.getErr().write((e.getMessage() + "\n").getBytes());
        } catch (IOException error) {
            e.addSuppressed(error);
            throw new RuntimeException("Unable to write to error stream!", e);
        }
    }

}
