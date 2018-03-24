package ru.spbau.cliapp

import ru.spbau.cliapp.task.Task

/**
 * Class for keeping [Task] instances by names along with some default fallback task.
 */
class TasksRegistry(val defaultTask: Task, tasks: Map<String, Task> = emptyMap()) {
    val tasks: MutableMap<String, Task> = tasks.toMutableMap()

    /**
     * Adds task by this name into the registry, replaces existing.
     */
    fun addTask(name: String, task: Task) {
        tasks[name] = task
    }

    /**
     * Returns task found by this name or default.
     */
    fun getTask(name: String) = tasks.getOrDefault(name, defaultTask)

}
