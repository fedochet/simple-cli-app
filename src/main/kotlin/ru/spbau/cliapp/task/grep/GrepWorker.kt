package ru.spbau.cliapp.task.grep

/**
 * This class represents single match of grep parser.
 */
data class GrepMatch(val value: String, val range: IntRange)

/**
 * This class does all grep heavy-lifting when it comes to actual parsing.
 */
class GrepWorker(pattern: String, ignoreCase: Boolean = false, private val wordRegexp: Boolean = false) {
    private val pattern = pattern.toRegexWithParams(ignoreCase)

    /**
     * @return list of [GrepMatch] as they appeared in [line]. Empty list means no matches.
     */
    fun findMatches(line: String): List<GrepMatch> {
        val wordsRanges = findWordRanges(line)

        return pattern.findAll(line)
                .filter { !wordRegexp || it.range in wordsRanges }
                .map { GrepMatch(it.value, it.range) }
                .toList()
    }

    companion object {
        private val wordRegex = Regex("\\w+")
        private fun findWordRanges(line: String) = wordRegex.findAll(line).map { it.range }.toSet()
    }
}

fun List<GrepMatch>.replace(s: String, operator: (GrepMatch) -> String): String {
    return this.foldRight(s, { match, acc -> acc.replaceRange(match.range, operator(match)) })
}

private fun String.toRegexWithParams(ignoreCase: Boolean): Regex {
    return if (ignoreCase) this.toRegex(RegexOption.IGNORE_CASE) else this.toRegex()
}