package ru.spbau.cliapp;

import ru.spbau.cliapp.core.TaskInfo;
import ru.spbau.cliapp.core.Workflow;
import ru.spbau.cliapp.task.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Application {
    public static void main(String[] args) throws IOException {
        Cat cat = new Cat();
        Echo echo = new Echo();
        Map<String, Task> namedTasks = new HashMap<>();
        namedTasks.put("cat", cat);
        namedTasks.put("echo", echo);
        namedTasks.put("wc", new Wc());
        namedTasks.put("pwd", new Pwd());

        Workflow workflow = new Workflow(
            namedTasks, Arrays.asList(
//                new TaskInfo("echo", Arrays.asList("hello", "world")),
//                new TaskInfo("cat")
                new TaskInfo("cat", Arrays.asList("xorg.conf"))
            )
        );

        workflow.execute(Paths.get("/home/roman"), System.in, System.out, System.err);
    }
}
