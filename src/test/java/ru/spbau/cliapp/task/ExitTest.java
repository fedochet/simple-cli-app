package ru.spbau.cliapp.task;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.spbau.cliapp.core.EXIT;
import ru.spbau.cliapp.core.StopInterpreterException;

import static org.assertj.core.api.Assertions.*;

class ExitTest {

    @Test
    void exit_command_throws_StopInterpreterException() {
        Exit exit = new Exit();
        assertThat(exit.main(null, null)).isEqualTo(EXIT.INSTANCE);
    }
}