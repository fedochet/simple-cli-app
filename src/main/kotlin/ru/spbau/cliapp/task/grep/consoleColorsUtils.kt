package ru.spbau.cliapp.task.grep

private val ANSI_RESET = "\u001B[0m"
private val ANSI_RED = "\u001B[31m"
private val ANSI_RED_BOLD = "\u001b[1;31m"

enum class Color(val value: String) {
    RED(ANSI_RED),
    RED_BOLD(ANSI_RED_BOLD)
}

fun String.ansiColored(c: Color) = c.value + this + ANSI_RESET
