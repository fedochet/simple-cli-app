package ru.spbau.cliapp.task;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

public class Wc implements Task {
    @Override
    public int main(InputStream stdin, OutputStream stdout, List<String> args) {
        long newlines = 0;
        int bytesCount = 0;
        long words = 0;

        ByteArrayOutputStream bytesBuffer = new ByteArrayOutputStream();
        if (args.isEmpty()) {
            int size;
            byte[] buffer = new byte[1024];
            try {
                while ((size = stdin.read(buffer)) != -1) {
                    bytesBuffer.write(buffer, 0, size);
                }

                bytesCount = bytesBuffer.size();
                String result = bytesBuffer.toString();
                words = countWords(result);
                newlines = countNewlines(result);

                stdout.write(MessageFormat.format("{0} {1} {2}\n", newlines, words, bytesCount).getBytes());
            } catch (IOException e) {
                return 1;
            }
        }

        return 0;
    }

    private long countNewlines(String result) {
        return result.chars().filter(c -> (char)c == '\n').count();
    }

    private long countWords(String result) {
        return Arrays.stream(result.split("\n"))
            .filter(s -> !s.isEmpty())
            .mapToLong(s -> s.split("\\s+").length)
            .sum();
    }
}
