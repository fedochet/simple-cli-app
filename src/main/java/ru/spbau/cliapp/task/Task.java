package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

public interface Task {
    @NotNull
    TaskStatus main(@NotNull ProcessContext context, @NotNull List<@NotNull String> args);
}
