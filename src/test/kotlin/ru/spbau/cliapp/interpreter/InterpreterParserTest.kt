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

    @Test
    fun `expansion is performed in double qouted strings`() {
        assertThat(parser.parse("echo \"\$hello\"", mapOf("hello" to "world")))
                .containsExactly(TaskInfo("echo", listOf("world")))
    }

    @Test
    fun `no expansion performed in single quoted strings`() {
        assertThat(parser.parse("echo '\$hello'", mapOf("hello" to "world")))
                .containsExactly(TaskInfo("echo", listOf("\$hello")))
    }

    @Test
    fun `double quoted string is not splitted after expansion`() {
        assertThat(parser.parse("echo \"one \$hello world\"", mapOf("hello" to "world")))
                .containsExactly(TaskInfo("echo", listOf("one world world")))
    }
}