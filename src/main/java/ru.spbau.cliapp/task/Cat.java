package ru.spbau.cliapp.task;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Cat extends AbstractTask {

    protected int main(InputStream stdin, OutputStream stdout, String[] args) {
        if (args.length == 0) {
            int size;
            byte[] bytes = new byte[1024];
            try {
                while ((size = stdin.read(bytes)) != -1) {
                    stdout.write(bytes, 0, size);
                }
            } catch (IOException e) {
                return 1;
            }
        }

        return 0;
    }
}
