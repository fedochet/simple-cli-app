package ru.spbau.cliapp.parsing

/**
 * Exception to signal that parsing went wrong.
 */
class ParsingException(reason: String) : IllegalArgumentException(reason)