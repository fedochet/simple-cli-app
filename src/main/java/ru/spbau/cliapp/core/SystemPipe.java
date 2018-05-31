package ru.spbau.cliapp.core;

import java.io.*;

/**
 * This is a simple implementation of {@link Pipe} concept using {@link PipedInputStream} and {@link PipedOutputStream}.
 */
public class SystemPipe implements Pipe {

    private static final int DEFAULT_BUFFER_SIZE = 16 * 1024; // 16 KB
    private final PipedInputStream input;
    private final PipedOutputStream output;

    public SystemPipe() throws IOException {
        input = new PipedInputStream(DEFAULT_BUFFER_SIZE);
        output = new PipedOutputStream(input);
    }

    @Override
    public InputStream getInputStream() {
        return input;
    }

    @Override
    public OutputStream getOutputStream() {
        return output;
    }

    /**
     * This method does not closes input stream, because it is closed automatically after closing output.
     */
    @Override
    public void close() throws IOException {
        output.close();
    }
}
