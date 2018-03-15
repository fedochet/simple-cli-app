package ru.spbau.cliapp.interpreter


import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import ru.spbau.cliapp.core.TaskInfo
import ru.spbau.cliapp.parsing.StringInterpolator
import ru.spbau.cliapp.parsing.TaskInfoParser
import ru.spbau.cliapp.parsing.Tokenizer

class InterpreterParserTest {
    val parser = InterpreterParser(Tokenizer, TaskInfoParser, StringInterpolator)

    @Test
    fun `simple parsing example`() {
        assertThat(parser.parse("echo hello world | cat | wc", emptyMap()))
                .containsExactly(
                        TaskInfo("echo", listOf("hello", "world")),
                        TaskInfo("cat"),
                        TaskInfo("wc")
                )
    }

    @Test
    fun `complex parsing exapmple`() {
        assertThat(parser.parse("echo \$x | \$wc", mapOf("x" to "hello | cat", "wc" to "less -f")))
                .containsExactly(
                        TaskInfo("echo", listOf("hello")),
                        TaskInfo("cat"),
                        TaskInfo("less", listOf("-f"))
                )
    }
}