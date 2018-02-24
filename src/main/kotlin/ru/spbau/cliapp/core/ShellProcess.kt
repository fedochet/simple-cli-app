package ru.spbau.cliapp.core

import java.io.InputStream
import java.io.OutputStream

interface ShellProcess {
    fun run(stdin: InputStream, stdout: OutputStream)
}