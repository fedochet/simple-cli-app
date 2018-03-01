package ru.spbau.cliapp.task;

import java.io.InputStream;
import java.io.OutputStream;

public interface Process {
    int execute(InputStream stdin, OutputStream stdout, String[] args);
}
