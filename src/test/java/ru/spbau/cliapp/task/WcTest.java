package ru.spbau.cliapp.task;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;

class WcTest {
    private Wc wc = new Wc();

    @Test
    void empty_string_gives_correct_answer() {
        ByteArrayInputStream emptyStringInput = new ByteArrayInputStream("".getBytes());
        ByteOutputStream output = new ByteOutputStream();

        wc.main(emptyStringInput, output, emptyList());

        String result = new String(output.getBytes(), 0, output.getCount());
        assertEquals("0 0 0\n", result);
    }
}