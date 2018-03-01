package ru.spbau.cliapp.task;

import org.junit.jupiter.api.Test;
import ru.spbau.cliapp.core.StopInterpreterException;

import static org.junit.jupiter.api.Assertions.*;

class ExitTest {

    @Test
    void exit_command_throws_StopInterpreterException() {
        Exit exit = new Exit();

        assertThrows(StopInterpreterException.class,
            () -> exit.main(null, null, null)
        );
    }
}