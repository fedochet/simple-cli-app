package ru.spbau.cliapp.task;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.spbau.cliapp.task.TestUtil.createInput;

class CatTest {

    private Cat cat = new Cat();

    @Test
    void cat_prints_from_stdin_to_stdout_when_no_args() {
        String original = "hello, world!";
        InputStream input = createInput(original);
        OutputStream output = new ByteArrayOutputStream();

        cat.main(input, output, Collections.emptyList());

        assertEquals(original, output.toString());
    }

}