package ru.spbau.cliapp;

import ru.spbau.cliapp.interpreter.Interpreter;
import ru.spbau.cliapp.task.*;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Map<String, Task> taskRegistry = new HashMap<>();
        taskRegistry.put("cat", new Cat());
        taskRegistry.put("echo", new Echo());
        taskRegistry.put("exit", new Exit());
        taskRegistry.put("pwd", new Pwd());
        taskRegistry.put("wc", new Wc());

        Interpreter interpreter = new Interpreter(Paths.get(""), taskRegistry);
        interpreter.run(System.in, System.out, System.err);
    }
}
