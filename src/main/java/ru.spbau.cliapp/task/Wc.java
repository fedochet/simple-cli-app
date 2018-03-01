package ru.spbau.cliapp.task;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Wc implements Task {
    @Override
    public int main(InputStream stdin, OutputStream stdout, List<String> args) {
        return 0;
    }
}
