package ru.spbau.cliapp.task.grep

import picocli.CommandLine
import ru.spbau.cliapp.core.ERROR
import ru.spbau.cliapp.core.ProcessContext
import ru.spbau.cliapp.core.SUCCESS
import ru.spbau.cliapp.core.TaskStatus
import ru.spbau.cliapp.task.Task
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

/**
 * This task emulates unix util `grep`.
 *
 * Usage: `grep options... pattern files...`
 *
 * See options described in class [GrepParams].
 */
class GrepTask : Task {
    override fun main(context: ProcessContext, args: List<String>): TaskStatus {
        try {
            val grepParams = CommandLine.populateCommand(GrepParams(), *args.toTypedArray())

            if (grepParams.fileNames.isEmpty()) {
                return executeGrep(context.stdin, context.stdout, grepParams)
            } else {
                grepParams.fileNames.forEach { filename ->
                    try {
                        val targetFile = context.workingDir.resolve(filename).toFile()
                        targetFile.inputStream().use { executeGrep(it, context.stdout, grepParams) }
                    } catch (e: IOException) {
                        context.err.println("Error: ${e.message}")
                    }
                }
            }

            return SUCCESS
        } catch (e: CommandLine.ParameterException) {
            context.err.println(e.message)
            return ERROR(e)
        }
    }

    private fun executeGrep(inputStream: InputStream, outputStream: OutputStream, grepParams: GrepParams): TaskStatus {
        val worker = GrepWorker(grepParams.pattern, grepParams.ignoreCase, grepParams.wordRegexp)
        val contextSize = grepParams.afterContext ?: 0

        val bufferedReader = inputStream.bufferedReader()

        var contextToPrint = 0
        while (true) {
            val line = bufferedReader.readLine() ?: break

            val matches = worker.findMatches(line)

            if (matches.isNotEmpty()) {
                val fixedLine = matches.replace(line, { it.value.ansiColored(Color.RED_BOLD) })
                outputStream.println(fixedLine)
                contextToPrint = contextSize
            } else if (contextToPrint > 0) {
                outputStream.println(line)
                contextToPrint -= 1
            }
        }

        return SUCCESS
    }
}

private fun OutputStream.println(s: String?) {
    this.write("${s ?: ""}\n".toByteArray())
    this.flush()
}
