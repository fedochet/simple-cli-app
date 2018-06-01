package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.ERROR;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.SUCCESS;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.IOException;
import java.util.List;

/**
 * This task emulates unix `pwd` util - it prints name of current/working directory.
 */
public class Pwd implements Task {
    @Override
    @NotNull
    public TaskStatus main(@NotNull ProcessContext context, @NotNull List<String> args) {
        try {
            context.getStdout().write((context.getWorkingDir().toAbsolutePath().toString() + "\n").getBytes());
        } catch (IOException e) {
            return new ERROR(e);
        }

        return SUCCESS.INSTANCE;
    }

}
