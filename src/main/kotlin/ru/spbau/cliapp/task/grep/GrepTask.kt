package ru.spbau.cliapp.task.grep

import picocli.CommandLine
import ru.spbau.cliapp.core.ERROR
import ru.spbau.cliapp.core.ProcessContext
import ru.spbau.cliapp.core.SUCCESS
import ru.spbau.cliapp.core.TaskStatus
import ru.spbau.cliapp.task.Task
import java.io.OutputStream
import java.util.*

class GrepTask : Task {
    override fun main(context: ProcessContext, args: List<String>): TaskStatus {
        try {
            val grepParams = CommandLine.populateCommand(GrepParams(), *args.toTypedArray())

            if (grepParams.fileNames.isEmpty()) {
                return executeGrepOnStdin(context, grepParams)
            }

            return SUCCESS
        } catch (e: CommandLine.ParameterException) {
            context.err.println(e.message)
            return ERROR(e)
        }
    }

    private fun executeGrepOnStdin(context: ProcessContext, grepParams: GrepParams): TaskStatus {
        val pattern = grepParams.pattern.toRegex()

        val bufferedReader = Scanner(context.stdin)

        while (bufferedReader.hasNextLine()) {
            val line = bufferedReader.nextLine()
            if (pattern.containsMatchIn(line)) {
                context.stdout.println(pattern.replace(line, { it.value.ansiColored(Color.RED_BOLD) }))
            }
        }

        return SUCCESS
    }
}

private fun OutputStream.println(s: String?) {
    this.write("${s ?: ""}\n".toByteArray())
    this.flush()
}
