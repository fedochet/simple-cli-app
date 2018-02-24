package ru.spbau.cliapp.task

import java.io.InputStream
import java.io.OutputStream

class TaskContext(val stdin: InputStream, val stdout: OutputStream, val _args: Array<String>)