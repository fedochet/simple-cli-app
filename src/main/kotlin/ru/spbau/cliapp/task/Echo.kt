package ru.spbau.cliapp.task

import java.io.InputStream
import java.io.PrintStream

class Echo(
        private val _in: InputStream = System.`in`,
        private val _out: PrintStream = System.out
) {

    fun main(args: Array<String>) {
        _out.println(args.joinToString(" "))
    }

}