package ru.spbau.cliapp.task;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class WcTest {
    private Wc wc = new Wc();

    @Test
    void empty_string_gives_correct_answer() {
        InputStream emptyStringInput = getInput("");
        ByteOutputStream output = new ByteOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("0 0 0\n", output.toString());
    }

    @Test
    void hello_world_string_gives_correct_result() {
        InputStream emptyStringInput = getInput("hello world");
        ByteOutputStream output = new ByteOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("0 2 11\n", output.toString());
    }

    @Test
    void newlines_are_correctly_counted() {
        InputStream emptyStringInput = getInput("hello\nworld\n");
        ByteOutputStream output = new ByteOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("2 2 12\n", output.toString());
    }

    @NotNull
    private InputStream getInput(String s) {
        return new ByteArrayInputStream(s.getBytes());
    }
}