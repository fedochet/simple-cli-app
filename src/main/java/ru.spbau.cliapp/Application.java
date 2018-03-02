package ru.spbau.cliapp;

import ru.spbau.cliapp.interpreter.Interpreter;

import java.nio.file.Paths;

public class Application {
    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter(Paths.get(""));
        interpreter.run(System.in, System.out, System.err);
    }
}
