package ru.spbau.cliapp.core

/**
 * This class represents info about executable task like `echo` or `cat`.
 */
data class TaskInfo (val taskName: String, val arguments: List<String> = emptyList())
