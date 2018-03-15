package ru.spbau.cliapp;

import ru.spbau.cliapp.interpreter.Interpreter;
import ru.spbau.cliapp.parsing.TaskInfoParser;
import ru.spbau.cliapp.parsing.Tokenizer;
import ru.spbau.cliapp.task.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Application {
    private static final Map<String, Task> taskRegistry = new HashMap<>();

    static {
        taskRegistry.put("cat", new Cat());
        taskRegistry.put("echo", new Echo());
        taskRegistry.put("exit", new Exit());
        taskRegistry.put("pwd", new Pwd());
        taskRegistry.put("wc", new Wc());
    }

    public static void main(String[] args) throws IOException {
        Interpreter interpreter = new Interpreter(
            Paths.get(""),
            taskRegistry,
            Tokenizer.INSTANCE,
            TaskInfoParser.INSTANCE
        );

        interpreter.run(System.in, System.out, System.err);
    }
}
