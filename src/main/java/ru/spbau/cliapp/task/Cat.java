package ru.spbau.cliapp.task;

import org.jetbrains.annotations.NotNull;
import ru.spbau.cliapp.core.ERROR;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.SUCCESS;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Cat implements Task {

    @Override
    @NotNull
    public TaskStatus main(@NotNull ProcessContext context, @NotNull List<String> args) {
        if (args.isEmpty()) {
            return printFromStdout(context);
        } else {
            return printFiles(context, args);
        }

    }

    private TaskStatus printFiles(ProcessContext context, List<String> args) {
        TaskStatus errorCode = SUCCESS.INSTANCE;
        for (String fileName : args) {
            try {
                Path path = context.getWorkingDir().resolve(fileName);
                byte[] bytes = Files.readAllBytes(path);
                context.getStdout().write(bytes);
                context.getStdout().write("\n".getBytes());
            } catch (IOException e) {
                errorCode = new ERROR(e);
                try {
                    context.getErr().write(("Error priniting file " + fileName + "\n").getBytes());
                } catch (IOException ignored) {
                }
            }
        }

        return errorCode;
    }

    private TaskStatus printFromStdout(ProcessContext context) {
        int readByte;
        try {
            while ((readByte = context.getStdin().read()) != -1) {
                context.getStdout().write(readByte);
            }
        } catch (IOException e) {
            return new ERROR(e);
        }

        return SUCCESS.INSTANCE;
    }
}
