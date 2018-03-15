package ru.spbau.cliapp.interpreter;

import ru.spbau.cliapp.core.TaskInfo;
import ru.spbau.cliapp.core.Workflow;
import ru.spbau.cliapp.parsing.TaskInfoParser;
import ru.spbau.cliapp.parsing.Tokenizer;
import ru.spbau.cliapp.task.Task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * This class serves as a main controller which gathers all modules together
 */
public class Interpreter {
    private final Path workingDir;
    private final Map<String, Task> taskRegistry;
    private final Tokenizer tokenizer;
    private final TaskInfoParser parser;

    public Interpreter(Path workingDir, Map<String, Task> taskRegistry, Tokenizer tokenizer, TaskInfoParser parser) {
        this.workingDir = workingDir;
        this.taskRegistry = taskRegistry;
        this.tokenizer = tokenizer;
        this.parser = parser;
    }

    public void run(InputStream in, OutputStream out, OutputStream err) throws IOException {
        Scanner scanner = new Scanner(in);
        PrintStream w = new PrintStream(out);

        printPreface(w);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();

            List<TaskInfo> tasks = parser.parse(tokenizer.tokenize(command));
            new Workflow(taskRegistry, tasks).execute(workingDir, in, out, err);
            printPreface(w);
        }
    }

    private void printPreface(PrintStream w) {
        w.print(workingDir.toAbsolutePath().toString() + "> ");
    }
}
