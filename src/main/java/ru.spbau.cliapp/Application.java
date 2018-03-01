package ru.spbau.cliapp;

import ru.spbau.cliapp.core.Pipe;
import ru.spbau.cliapp.core.SystemPipe;
import ru.spbau.cliapp.task.ShellProcess;
import ru.spbau.cliapp.task.Cat;
import ru.spbau.cliapp.task.Echo;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Application {
    public static void main(String[] args) throws IOException {
        Pipe pipe = new SystemPipe();
        Echo echo = new Echo();
        Cat cat = new Cat();
        ShellProcess.createProcess(echo).execute(System.in, pipe.getOutputStream(), Arrays.asList("hello", "world"));
        ShellProcess.createProcess(cat).execute(pipe.getInputStream(), System.out, Collections.emptyList());
    }
}
