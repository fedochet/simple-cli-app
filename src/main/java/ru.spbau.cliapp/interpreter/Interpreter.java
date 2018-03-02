package ru.spbau.cliapp.interpreter;

import ru.spbau.cliapp.core.BasicProcessContext;
import ru.spbau.cliapp.task.Task;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

/**
 * This class serves as a main controller which gathers all modules together
 */
public class Interpreter {
    private final Path workingDir;
    private final Map<String, Task> taskRegistry;

    public Interpreter(Path workingDir, Map<String, Task> taskRegistry) {
        this.workingDir = workingDir;
        this.taskRegistry = taskRegistry;
    }

    public void run(InputStream in, OutputStream out, OutputStream err) {
        Scanner scanner = new Scanner(in);
        PrintStream w = new PrintStream(out);

        printPreface(w);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            Task task = taskRegistry.get(command);
            task.main(new BasicProcessContext(workingDir, in, out, err), Collections.emptyList());
            printPreface(w);
        }
    }

    private void printPreface(PrintStream w) {
        w.print(workingDir.toAbsolutePath().toString() + "> ");
    }
}
