package ru.spbau.cliapp.parsing

sealed class Token
data class StringToken(val value: String) : Token()
data class DoubleQuotesToken(val value: String) : Token()
data class SingleQuotesToken(val value: String) : Token()
object VerticalBar : Token()
object Equals : Token()

object Tokenizer {
    private val singleQuotes = "('[^']*')"
    private val doubleQuotes = "(\"[^\"]*\")"
    private val verticalBar = "(\\|)"
    private val equalsSign = "(=)"
    private val otherSymbols: String = "([^'\"|=]+)"
    private val splitRegex = Regex("${singleQuotes}|${doubleQuotes}|${verticalBar}|${equalsSign}|${otherSymbols}")
    private val singleQuotesRegex = Regex("'[^']*'")
    private val doubleQuotesRegex = Regex("\"[^\"]*\"")

    fun tokenize(s: String): List<Token> {
        return splitRegex.findAll(s)
                .map { it.value }
                .filter { it.isNotBlank() }
                .map { selectToken(it) }
                .flatMap { parseNonQuotes(it) }
                .toList()
    }

    private fun parseNonQuotes(it: Token) = when (it) {
        is StringToken -> it.value.split(" ").filter { it.isNotBlank() }.map { StringToken(it) }.asSequence()
        else -> sequenceOf(it)
    }

    private fun selectToken(token: String) = when {
        singleQuotesRegex matches token -> SingleQuotesToken(token.removeSurrounding("\'"))
        doubleQuotesRegex matches token -> DoubleQuotesToken(token.removeSurrounding("\""))
        token == "|" -> VerticalBar
        token == "=" -> Equals
        else -> StringToken(token)
    }

}