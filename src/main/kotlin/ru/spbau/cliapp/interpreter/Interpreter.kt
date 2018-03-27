package ru.spbau.cliapp.interpreter

import ru.spbau.cliapp.core.Workflow
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

    /**
     * Starts interpreter and binds it to passed streams. It will stop when EOL will be entered directly into
     * stdin.
     *
     * @param `input` stdin for shell
     * @param output stdout for shell
     * @param err stderr for shell
     * @throws IOException if anything goes wrong during tasks execution
     */
    @Throws(IOException::class)
    fun run(input: InputStream, output: OutputStream, err: OutputStream) {
        val scanner = Scanner(input)
        val w = PrintStream(output)

        printPreface(w)
        while (scanner.hasNextLine()) {
            val command = scanner.nextLine()

            val tasks = parser.parse(command, emptyMap())
            Workflow(taskRegistry, tasks).execute(workingDir, input, output, err)
            printPreface(w)
        }
    }

    private fun printPreface(w: PrintStream) {
        w.print(workingDir.toAbsolutePath().toString() + "> ")
    }
}
