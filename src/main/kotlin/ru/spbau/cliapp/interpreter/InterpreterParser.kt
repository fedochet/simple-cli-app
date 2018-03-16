package ru.spbau.cliapp.interpreter

import ru.spbau.cliapp.core.TaskInfo
import ru.spbau.cliapp.parsing.*

/**
 * This class coordinates all parsing utils in order to convert input string into a list of
 * actionable tasks (like shell tasks or variable assignments).
 *
 * It is supposed to be used directly in the [Interpreter] class.
 */
class InterpreterParser(
        private val tokenizer: Tokenizer,
        private val taskInfoParser: TaskInfoParser,
        private val interpolator: StringInterpolator) {

    /**
     * Main method for parsing strings.
     *
     * Aware of environment variables and performs one pass of substitution.
     *
     * Performs substitution only for strings in double quotes and in tikens itself; in single-quoted strings
     * substitution is not performed.
     *
     * Examples of parsing:
     *
     * ```
     * "echo hello world", {} -> "echo", "hello", "world"
     * "echo $bob world", {"bob" -> "john" } -> "echo", "john", "world"
     * ```
     */
    fun parse(input: String, env: Map<String, String>): List<TaskInfo> {
        val interpolatedTokens = tokenizer.tokenize(input).flatMap { interpolate(it, env) }
        return taskInfoParser.parse(interpolatedTokens)
    }

    private fun interpolate(token: Token, env: Map<String, String>) = when (token) {
        is DoubleQuotesToken -> listOf(DoubleQuotesToken(interpolator.interpolate(token.value, env)))
        is StringToken -> tokenizer.tokenize(interpolator.interpolate(token.value, env))
        else -> listOf(token)
    }
}