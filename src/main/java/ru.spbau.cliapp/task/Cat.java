package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Cat implements Task {

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
        if (args.length == 0) {
            int size;
            byte[] bytes = new byte[1024];
            try {
                while ((size = stdin.read(bytes)) != -1) {
                    stdout.write(bytes, 0, size);
                }
            } catch (IOException e) {
                return 1;
            }
        }

        return 0;
    }
}
