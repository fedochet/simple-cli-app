package ru.spbau.cliapp.task;

import ru.spbau.cliapp.core.ERROR;
import ru.spbau.cliapp.core.ProcessContext;
import ru.spbau.cliapp.core.SUCCESS;
import ru.spbau.cliapp.core.TaskStatus;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class Echo implements Task {

    @Override
    public TaskStatus main(ProcessContext context, List<String> args) {
        try {
            String result = args.stream().collect(Collectors.joining(" ")).concat("\n");
            context.getStdout().write(result.getBytes());
        } catch (IOException e) {
            return new ERROR(e);
        }

        return SUCCESS.INSTANCE;
    }
}
