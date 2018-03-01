package ru.spbau.cliapp.core;

import java.util.List;

public class TaskInfo {
    public final String taskName;
    public final List<String> arguments;

    public TaskInfo(String taskName, List<String> arguments) {
        this.taskName = taskName;
        this.arguments = arguments;
    }
}
