package ru.spbau.cliapp.task;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import static java.util.Collections.emptyList;
import static org.junit.Assert.assertEquals;
import static ru.spbau.cliapp.task.TestUtil.createInput;

public class WcTest {
    private Wc wc = new Wc();

    @Test
    public void empty_string_gives_correct_answer() {
        InputStream emptyStringInput = createInput("");
        OutputStream output = new ByteArrayOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("0 0 0\n", output.toString());
    }

    @Test
    public void hello_world_string_gives_correct_result() {
        InputStream emptyStringInput = createInput("hello world");
        OutputStream output = new ByteArrayOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("0 2 11\n", output.toString());
    }

    @Test
    public void newlines_are_correctly_counted() {
        InputStream emptyStringInput = createInput("hello\nworld\n");
        OutputStream output = new ByteArrayOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        assertEquals("2 2 12\n", output.toString());
    }

}