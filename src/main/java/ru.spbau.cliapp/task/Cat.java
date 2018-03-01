package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Cat implements Task {

    @Override
    public int main(InputStream stdin, OutputStream stdout, List<String> args) {
        if (args.isEmpty()) {
            int size;
            byte[] bytes = new byte[1024];
            try {
                while ((size = stdin.read(bytes)) != -1) {
                    stdout.write(bytes, 0, size);
                }
            } catch (IOException e) {
                return 1;
            }
        } else {
            for (String fileName : args) {
                try {
                    Path path = Paths.get(fileName);
                    byte[] bytes = Files.readAllBytes(path);
                    stdout.write(bytes);
                    stdout.write("\n".getBytes());
                } catch (IOException ignored) {
                }
            }
        }

        return 0;
    }
}
