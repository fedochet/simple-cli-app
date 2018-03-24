package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.ProcessContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class NativeProcess implements Task {
    private final String taskName;
    private final Path inputFile;
    private final Path outputFile;

    public NativeProcess(String taskName) throws IOException {
        this.taskName = taskName;
        inputFile = Files.createTempFile("native_process_input", ".tmp");
        outputFile = Files.createTempFile("native_process_input", ".tmp");
    }

    @Override
    public int main(ProcessContext context, List<String> args) {
        try {
            return executeNativeProcess(context, args);
        } catch (IOException | InterruptedException e) {
            return 1;
        }
    }

    private int executeNativeProcess(ProcessContext context, List<String> args) throws IOException, InterruptedException {
        List<String> commands = new ArrayList<>();

        readAllInputToInputFile(context.getStdin());

        commands.add(taskName);
        commands.addAll(args);
        Process start = new ProcessBuilder()
            .command(commands)
            .redirectInput(inputFile.toFile())
            .redirectOutput(outputFile.toFile())
            .directory(context.getWorkingDir().toFile())
            .start();

        start.waitFor();

        writeAllOutputFromOutputFile(context.getStdout());

        return 0;
    }

    private void readAllInputToInputFile(InputStream stdin) throws IOException {
        int read;
        try (Writer writer = Files.newBufferedWriter(inputFile)) {
            while ((read = stdin.read()) != -1) {
                writer.write(read);
            }
        }
    }

    private void writeAllOutputFromOutputFile(OutputStream stdout) throws IOException {
        int read;
        try (Reader reader = Files.newBufferedReader(inputFile)) {
            while ((read = reader.read()) != -1) {
                stdout.write(read);
            }
        }
    }

}
