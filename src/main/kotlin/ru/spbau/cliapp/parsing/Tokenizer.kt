package ru.spbau.cliapp.parsing

sealed class Token
data class StringToken(val value: String) : Token()
data class DoubleQuotesToken(val value: String) : Token()
data class SingleQuotesToken(val value: String) : Token()

class Tokenizer {
    val splitRegex = Regex("('[^']*')|(\"[^\"]*\")|([^'\"]+)")
    val singleQuotesRegex = Regex("'[^']*'")
    val doubleQuotesRegex = Regex("\"[^\"]*\"")

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
        else -> StringToken(token)
    }

}