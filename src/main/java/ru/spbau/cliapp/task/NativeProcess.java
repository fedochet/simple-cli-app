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
    private final Path errorsFile;

    public NativeProcess(String taskName) {
        this.taskName = taskName;
        try {
            inputFile = Files.createTempFile("native_process_input", ".tmp");
            outputFile = Files.createTempFile("native_process_output", ".tmp");
            errorsFile = Files.createTempFile("native_process_errors", ".tmp");
        } catch (IOException e) {
            throw new RuntimeException("Cannot create temporary files for process " + taskName, e);
        }
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
        Process start = new ProcessBuilder(commands)
            .redirectInput(inputFile.toFile())
            .redirectOutput(outputFile.toFile())
            .redirectError(errorsFile.toFile())
            .directory(context.getWorkingDir().toAbsolutePath().toFile())
            .start();

        start.waitFor();

        writeToFile(context.getStdout(), outputFile);
        writeToFile(context.getErr(), errorsFile);

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

    private void writeToFile(OutputStream stdout, Path file) throws IOException {
        int read;
        try (Reader reader = Files.newBufferedReader(file)) {
            while ((read = reader.read()) != -1) {
                stdout.write(read);
            }
        }
    }

}
