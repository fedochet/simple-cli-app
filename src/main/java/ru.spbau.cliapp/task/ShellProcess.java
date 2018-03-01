package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ShellProcess {
    private final Task task;

    public ShellProcess(Task task) {
        this.task = task;
    }

    public int execute(InputStream stdin, OutputStream stdout, String[] args) {
        int exitCode = task.main(stdin, stdout, args);
        try {
            stdout.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output stream", e);
        }
        return exitCode;

    }
}
