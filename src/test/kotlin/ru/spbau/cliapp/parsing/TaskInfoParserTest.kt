package ru.spbau.cliapp.parsing

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.spbau.cliapp.core.TaskInfo

class TaskInfoParserTest {

    val parser = TaskInfoParser

    @Test
    fun `empty list of tasks is returned on empty list of tokens`() {
        assertThat(parser.parse(emptyList())).isEmpty()
    }

    @Test
    fun `single string token is treated as command name`() {
        assertThat(parser.parse(listOf(StringToken("command"))))
                .containsExactly(TaskInfo("command"))
    }

    @Test
    fun `many string tokens in row are treated as command and its arguments`() {
        val tokensInRow = listOf(StringToken("command"), StringToken("arg1"), StringToken("arg2"), StringToken("arg3"))

        assertThat(parser.parse(tokensInRow))
                .containsExactly(TaskInfo("command", listOf("arg1", "arg2", "arg3")))
    }
}