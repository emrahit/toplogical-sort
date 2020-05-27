package it.emrah.command.processor.service;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.emrah.command.processor.model.Task;
import it.emrah.command.processor.model.rest.TaskRequest;
import it.emrah.command.processor.model.rest.TaskRequestEntity;

public class TaskConverter {

    public static Map<String, Task> convertTasks(TaskRequest taskRequest) {

        var tasks = taskRequest.getTasks();
        tasks.sort(Comparator.comparing(TaskRequestEntity::getName));


        return constructGraph(tasks);
    }

    private static  Map<String, Task> constructGraph(List<TaskRequestEntity> tasks) {
        Map<String, Task> taskGraph = new HashMap<>();
        for (var entity : tasks) {
            Task task = new Task(entity.getName(), entity.getCommand(), entity.getRequires());
            taskGraph.put(entity.getName(), task);
        }

        return taskGraph;
    }
}
