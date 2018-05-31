package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.EXIT;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

public class Exit implements Task {
    @Override
    @NotNull
    public TaskStatus main(@NotNull ProcessContext e, @NotNull List<String> args) {
        return EXIT.INSTANCE;
    }
}
