package ru.spbau.cliapp.core

import ru.spbau.cliapp.task.Task

/**
 * This class represents executed process and requires [Task] to be constructed from.
 */
class ShellProcess(private val task: Task) {

    /**
     * @param context is a execution context which is passed to task.
     * @param args are string arguments fot task's main method.
     * @return status of the executed task.
     */
    fun execute(context: ProcessContext, args: List<String>): TaskStatus {
        context.stdout.use {
            return task.main(context, args)
        }
    }
}
