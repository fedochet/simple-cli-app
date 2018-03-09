package ru.spbau.cliapp.parsing

import ru.spbau.cliapp.core.TaskInfo
import java.util.Arrays.asList

/**
 * This class takes a list of [Token] objects and converts them into [TaskInfo] objects.
 *
 * This class does not perform any kind of substitution.
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

        return asList(TaskInfo(tokens.first().value, tokens.tail().map { it.value }))

    }

    private fun <T> List<T>.tail() = drop(1)

}