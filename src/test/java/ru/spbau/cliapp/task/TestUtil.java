package ru.spbau.cliapp.task;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public final class TestUtil {
    private TestUtil() {}
    static public InputStream createInput(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}
