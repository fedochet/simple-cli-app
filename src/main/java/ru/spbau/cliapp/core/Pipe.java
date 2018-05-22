package ru.spbau.cliapp.core;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class represents structure like OS pipe.
 *
 * Semantics is that whatever is written into input stream appears from output stream.
 *
 * It implements {@link Closeable} because such pipe should be closed at once.
 */
public interface Pipe extends Closeable {
    InputStream getInputStream();
    OutputStream getOutputStream();
}
