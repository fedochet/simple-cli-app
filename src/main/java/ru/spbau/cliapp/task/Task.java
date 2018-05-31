package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

public interface Task {
    TaskStatus main(ProcessContext context, List<String> args);
}
