package ru.spbau.cliapp.interpreter;

import ru.spbau.cliapp.TasksRegistry;
import ru.spbau.cliapp.core.TaskInfo;
import ru.spbau.cliapp.core.Workflow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class serves as a main controller which gathers all modules together
 */
public class Interpreter {
    private final Path workingDir;
    private final TasksRegistry taskRegistry;
    private final InterpreterParser parser;

    public Interpreter(Path workingDir, TasksRegistry taskRegistry, InterpreterParser parser) {
        this.workingDir = workingDir;
        this.taskRegistry = taskRegistry;
        this.parser = parser;
    }

    /**
     * Starts interpreter and binds it to passed streams. It will stop when EOL will be entered directly into
     * stdin.
     *
     * @param in stdin for shell
     * @param out stdout for shell
     * @param err stderr for shell
     * @throws IOException if anything goes wrong during tasks execution
     */
    public void run(InputStream in, OutputStream out, OutputStream err) throws IOException {
        Scanner scanner = new Scanner(in);
        PrintStream w = new PrintStream(out);

        printPreface(w);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            List<TaskInfo> tasks = parser.parse(command, Collections.emptyMap());
            new Workflow(taskRegistry, tasks).execute(workingDir, in, out, err);
            printPreface(w);
        }
    }

    private void printPreface(PrintStream w) {
        w.print(workingDir.toAbsolutePath().toString() + "> ");
    }
}
