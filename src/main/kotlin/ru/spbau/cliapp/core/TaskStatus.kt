package ru.spbau.cliapp.core

/**
 * This class represents status of task execution.
 *
 * - [SUCCESS] represents that task finished correctly.
 * - [EXIT] represents that this task required for shutdown of whole command line.
 * - [ERROR] represents some kind of error and contains message of what went wrong.
 */
sealed class TaskStatus

object SUCCESS : TaskStatus()
object EXIT : TaskStatus()
data class ERROR(val msg: String) : TaskStatus() {
    constructor(e: Exception): this(e.message ?: e.toString())
}