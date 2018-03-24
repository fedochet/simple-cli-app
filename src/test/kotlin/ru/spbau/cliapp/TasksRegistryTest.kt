package ru.spbau.cliapp

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import ru.spbau.cliapp.task.Task

class TasksRegistryTest {
    val defaultTask = mock(Task::class.java)
    val registry = TasksRegistry(defaultTask)

    @Test
    fun `if no task with such name registry returns default task`() {
        assertThat(registry.getTask("name")).isSameAs(defaultTask)
    }

    @Test
    internal fun `if task is present by name then it is returned`() {
        val taskMock = mock(Task::class.java)
        registry.addTask("name", taskMock)

        assertThat(registry.getTask("name")).isSameAs(taskMock)
    }
}