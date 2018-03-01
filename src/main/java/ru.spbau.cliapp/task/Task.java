package ru.spbau.cliapp.task;

import java.io.InputStream;
import java.io.OutputStream;

public interface Task {
    int execute(InputStream stdin, OutputStream stdout, String[] args);
}
