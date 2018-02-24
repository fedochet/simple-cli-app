package ru.spbau.cliapp

import ru.spbau.cliapp.task.TaskBuilder
import java.io.PipedInputStream
import java.io.PipedOutputStream

fun main(args: Array<String>) {
    val echo = TaskBuilder {
        stdout.write(_args.joinToString(" ").toByteArray())
    }

    val cat = TaskBuilder {
        if (_args.isEmpty()) {
            val bytes = ByteArray(1024)

            while (true) {
                val read = stdin.read(bytes)
                if (read == -1) break
                stdout.write(bytes, 0, read)
            }
        }
    }

    val pipeIn = PipedInputStream()
    val pipeOut = PipedOutputStream(pipeIn)

    echo(System.`in`, pipeOut, arrayOf("hello", "world"))
    cat(pipeIn, System.out, emptyArray())
}