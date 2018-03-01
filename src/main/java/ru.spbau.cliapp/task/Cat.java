package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.BasicProcessContext;
import ru.spbau.cliapp.core.ProcessContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Cat implements Task {

    @Override
    public int main(InputStream stdin, OutputStream stdout, List<String> args) {
        return main(new BasicProcessContext(null, stdin, stdout, stdout), args);
    }

    @Override
    public int main(ProcessContext context, List<String> args) {
        if (args.isEmpty()) {
            int size;
            byte[] bytes = new byte[1024];
            try {
                while ((size = context.getStdin().read(bytes)) != -1) {
                    context.getStdout().write(bytes, 0, size);
                }
            } catch (IOException e) {
                return 1;
            }
        } else {
            for (String fileName : args) {
                try {
                    Path path = context.getWorkingDir().resolve(fileName);
                    byte[] bytes = Files.readAllBytes(path);
                    context.getStdout().write(bytes);
                    context.getStdout().write("\n".getBytes());
                } catch (IOException ignored) {
                }
            }
        }

        return 0;
    }
}
