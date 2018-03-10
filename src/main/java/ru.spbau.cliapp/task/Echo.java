package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.stream.Collectors;

public class Echo implements Task {

    @Override
    public int main(InputStream stdin, OutputStream stdout, List<String> args) {
        try {
            String result = args.stream().collect(Collectors.joining(" ")).concat("\n");
            stdout.write(result.getBytes());
        } catch (IOException e) {
            return 1;
        }

        return 0;
    }
}
