package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.EXIT;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

public class Exit implements Task {
    @Override
    public TaskStatus main(ProcessContext e, List<String> args) {
        return EXIT.INSTANCE;
    }
}
