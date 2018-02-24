package ru.spbau.cliapp.task

import java.io.InputStream
import java.io.OutputStream

typealias Task = (stdin: InputStream, stdout: OutputStream, args: Array<String>) -> Unit

object TaskBuilder {
    operator fun invoke(main: TaskContext.() -> Unit): Task {
        return { stdin, stdout, args ->
            val taskContext = TaskContext(stdin, stdout, args)
            taskContext.main()
            taskContext.stdout.flush()
            taskContext.stdout.close()
        }
    }
}