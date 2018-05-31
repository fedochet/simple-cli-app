package ru.spbau.cliapp.interpreter

import ru.spbau.cliapp.core.*
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintStream
import java.nio.file.Path
import java.util.*

/**
 * This class serves as a main controller which gathers all modules together
 */
class Interpreter(
        private val workingDir: Path,
        private val taskRegistry: TasksRegistry,
        private val parser: InterpreterParser) {


    private val environment = mutableMapOf<String, String>()

    /**
     * Starts interpreter and binds it to passed streams. It will stop when EOL will be entered directly into
     * stdin, or any workflow returns [EXIT] status.
     *
     * Does not close any of passed streams.
     *
     * @param `input` stdin for shell
     * @param output stdout for shell
     * @param err stderr for shell
     * @throws IOException if anything goes wrong during tasks execution
     */
    @Throws(IOException::class)
    fun run(input: InputStream, output: OutputStream, err: OutputStream) {
        val scanner = Scanner(input)
        val writer = PrintStream(output)

        printPreface(writer)
        while (scanner.hasNextLine()) {
            val command = scanner.nextLine()

            val taskStatus = handleCommand(command, input, output, err)
            when (taskStatus) {
                is ERROR -> writer.println("Error during execution: ${taskStatus.msg}")
                EXIT -> {
                    writer.println("Exiting cli.")
                    return
                }
            }

            printPreface(writer)
        }
    }

    private fun handleCommand(command: String, input: InputStream, output: OutputStream, err: OutputStream): TaskStatus {
        val parsedResult = parser.parse(command, environment)
        return when (parsedResult) {

            is TasksPipeline -> executePipeline(parsedResult, input, output, err)

            is VarAssignmentInfo -> {
                environment[parsedResult.varName] = parsedResult.value
                SUCCESS
            }
        }
    }

    private fun executePipeline(pipeline: TasksPipeline, input: InputStream, output: OutputStream, err: OutputStream): TaskStatus {
        return Workflow(taskRegistry, pipeline.tasks).execute(workingDir, input, output, err)
    }

    private fun printPreface(w: PrintStream) {
        w.print("${workingDir.toAbsolutePath()}> ")
    }
}
