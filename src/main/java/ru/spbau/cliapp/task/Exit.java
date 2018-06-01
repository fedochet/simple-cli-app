package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.EXIT;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

/**
 * This task is a special task that signalises about end of cli session.
 *
 * Ideally, It is the only task that should return {@link EXIT} status.
 */
public class Exit implements Task {
    @Override
    @NotNull
    public TaskStatus main(@NotNull ProcessContext e, @NotNull List<String> args) {
        return EXIT.INSTANCE;
    }
}
