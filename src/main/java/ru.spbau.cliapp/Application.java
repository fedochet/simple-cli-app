package ru.spbau.cliapp;

import ru.spbau.cliapp.task.Echo;

public class Application {
    public static void main(String[] args) {
        Echo echo = new Echo();
        echo.main(System.in, System.out, new String[]{"hello", "world"});
    }
}
