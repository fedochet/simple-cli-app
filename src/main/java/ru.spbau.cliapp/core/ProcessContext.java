package ru.spbau.cliapp.core;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;

public interface ProcessContext {
    InputStream getStdin();
    OutputStream getStdout();
    OutputStream getErr();
    Path getWorkingDir();
}
