package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.ERROR;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.SUCCESS;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * This task emulates unix `wc` program.
 *
 * Print newline, word, and byte counts for each FILE, and a total line if more than one FILE is specified.
 * A word is a non-zero-length sequence of characters delimited by white space.
 *
 * With no FILE, read standard input.
 */
public class Wc implements Task {

    @Override
    @NotNull
    public TaskStatus main(@NotNull ProcessContext context, @NotNull List<String> args) {
        if (args.isEmpty()) {
            try {
                printlnWcResult(context.getStdout(), getCounts(context.getStdin()));
            } catch (IOException e) {
                return new ERROR(e);
            }
        } else {
            for (String fileName : args) {
                Path file = context.getWorkingDir().resolve(fileName);
                try (InputStream fileInputStream = Files.newInputStream(file)) {
                    WcResult counts = getCounts(fileInputStream);
                    printlnWcResult(context.getStdout(), counts);
                } catch (IOException ignored) {
                }

            }
        }

        return SUCCESS.INSTANCE;

    }

    private void printlnWcResult(OutputStream stdout, WcResult counts) throws IOException {
        stdout.write((counts + "\n").getBytes());
    }

    private WcResult getCounts(InputStream stdin) throws IOException {
        ByteArrayOutputStream bytesBuffer = new ByteArrayOutputStream();
        WcResult result = new WcResult();

        int lastByte;
        while ((lastByte = stdin.read()) != -1) {
            bytesBuffer.write(lastByte);
        }

        String wholeString = bytesBuffer.toString();

        result.bytesCount = bytesBuffer.size();
        result.words = countWords(wholeString);
        result.newlines = countNewlines(wholeString);

        return result;
    }

    private long countNewlines(String result) {
        return result.chars().filter(c -> (char) c == '\n').count();
    }

    private long countWords(String result) {
        return Arrays.stream(result.split("\n"))
            .filter(s -> !s.isEmpty())
            .mapToLong(s -> s.split("\\s+").length)
            .sum();
    }

    private class WcResult {
        long newlines = 0;
        int bytesCount = 0;
        long words = 0;

        @Override
        public String toString() {
            return MessageFormat.format("{0} {1} {2}", newlines, words, bytesCount);
        }
    }
}
