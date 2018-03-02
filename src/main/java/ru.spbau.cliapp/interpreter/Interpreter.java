package ru.spbau.cliapp.interpreter;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.Scanner;

public class Interpreter {
    private final Path workingDir;

    public Interpreter(Path workingDir) {
        this.workingDir = workingDir;
    }

    public void run(InputStream in, OutputStream out, OutputStream err) {
        Scanner scanner = new Scanner(in);
        PrintStream w = new PrintStream(out);

        printPreface(w);
        while (scanner.hasNextLine()) {
            String command = scanner.nextLine();
            w.println(command);
            printPreface(w);
        }
    }

    private void printPreface(PrintStream w) {
        w.print(workingDir.toAbsolutePath().toString() + "> ");
    }
}
