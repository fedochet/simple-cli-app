package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class AbstractTask implements Process {
    @Override
    public int execute(InputStream stdin, OutputStream stdout, String[] args) {
        int exitCode = main(stdin, stdout, args);
        try {
            stdout.close();
        } catch (IOException e) {
            throw new RuntimeException("Cannot close output stream", e);
        }
        return exitCode;

    }

    protected abstract int main(InputStream stdin, OutputStream stdout, String[] args);
}
