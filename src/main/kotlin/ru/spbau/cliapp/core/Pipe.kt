package ru.spbau.cliapp.core

import java.io.InputStream
import java.io.OutputStream

interface Pipe {
    fun openInputStream(): InputStream
    fun openOutputStream(): OutputStream
}