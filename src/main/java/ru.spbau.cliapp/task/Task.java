package ru.spbau.cliapp.task;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public interface Task {
    int main(InputStream stdin, OutputStream stdout, List<String> args);
}
