package ru.spbau.cliapp.task.grep

import picocli.CommandLine
import ru.spbau.cliapp.core.ERROR
import ru.spbau.cliapp.core.ProcessContext
import ru.spbau.cliapp.core.SUCCESS
import ru.spbau.cliapp.core.TaskStatus
import ru.spbau.cliapp.task.Task
import java.io.FileInputStream
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
        val pattern = grepParams.pattern.toRegexWithParams(grepParams.ignoreCase)
        val contextSize = grepParams.afterContext ?: 0

        val bufferedReader = inputStream.bufferedReader()

        var contextToPrint = 0
        while (true) {
            val line = bufferedReader.readLine() ?: break

            val wordsRanges = findWordRanges(line)

            val matches = pattern.findAll(line)
                    .filter { !grepParams.wordRegexp || it.range in wordsRanges }
                    .toList()

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

    companion object {
        private val wordRegex = Regex("\\w+")

        private fun findWordRanges(line: String) = wordRegex.findAll(line).map { it.range }.toSet()
    }
}

private fun List<MatchResult>.replace(s: String, operator: (MatchResult) -> String): String {
    return this.foldRight(s, { match, acc -> acc.replaceRange(match.range, operator(match)) })
}

private fun OutputStream.println(s: String?) {
    this.write("${s ?: ""}\n".toByteArray())
    this.flush()
}

private fun String.toRegexWithParams(ignoreCase: Boolean): Regex {
    return if (ignoreCase) this.toRegex(RegexOption.IGNORE_CASE) else this.toRegex()
}
