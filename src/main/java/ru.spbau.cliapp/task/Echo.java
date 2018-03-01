package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Echo implements Task {
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

    private int main(InputStream stdin, OutputStream stdout, String[] args) {
        try {
            for (String arg : args) {
                stdout.write(arg.getBytes());
                stdout.write(" ".getBytes());
            }
        } catch (IOException e) {
            return 1;
        }

        return 0;
    }
}
