package ru.spbau.cliapp.parsing

import ru.spbau.cliapp.core.TaskInfo

/**
 * This class takes a list of [Token] objects and converts them into [TaskInfo] objects.
 *
 * This class does not perform any kind of substitution. However, it should throw exceptions if there are
 * forbidden combination of tokens (for example, two pipes in a row).
 *
 * Any string token is treated like command. So, for example, tokens `["echo", "hello"]` will be parsed
 * into single `TaskInfo` with command `"echo"` and a single argument `"hello"`.
 */
object TaskInfoParser {

    /**
     * Takes list of tokens, groups them by pipe symbols (`|`) and then transforms them into TaskInfos.
     */
    fun parse(tokens: List<Token>): List<TaskInfo> {
        if (tokens.isEmpty()) return emptyList()

        return splitByPipes(tokens).map { TaskInfo(it.first().value, it.tail().map { it.value }) }
    }

    private fun splitByPipes(tokens: List<Token>): List<List<Token>> {
        val result = mutableListOf<MutableList<Token>>()
        result.add(mutableListOf())

        tokens.forEach {
            when (it) {
                VerticalBar -> result.add(mutableListOf())
                else -> result.last().add(it)
            }
        }

        return result
    }

    private fun <T> List<T>.tail() = drop(1)

}