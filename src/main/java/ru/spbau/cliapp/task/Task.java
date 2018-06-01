package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.TaskStatus;

import java.util.List;

/**
 * This is the main abstraction for the tasks to run in cli.
 *
 * In order to create task, you should implement this interface and its only method.
 */
public interface Task {

    /**
     * This method will be called when task is going to be executed.
     * @param context is a bundle for system resources and execution environment.
     * @param args are basic arguments (just strings separated by spaces).
     * @return status of task execution.
     */
    TaskStatus main(ProcessContext context, List<String> args);
}
