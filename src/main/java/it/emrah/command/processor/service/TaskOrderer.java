package it.emrah.command.processor.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.emrah.command.processor.model.Task;

public class TaskOrderer {

    private Set<String> visited = new HashSet<>();
    private List<Task> orderedTasks = new ArrayList<>();

    public TaskOrderer(Map<String, Task> tasksGraph) {
        for (var key : tasksGraph.keySet()) {
            buildDependencyOrder(key, tasksGraph);
        }

    }

    public List<Task> getOrderedTasks() {
        return orderedTasks;
    }

    public void buildDependencyOrder(String node, Map<String, Task> tasksGraph) {
        if (visited.contains(node)) {
            return;
        }
        Task task = tasksGraph.get(node);
        if (task.getRequires() != null && !task.getRequires().isEmpty()) {
            for (var requiredTask :
                    task.getRequires()) {
                buildDependencyOrder(requiredTask, tasksGraph);
            }
        }
        visited.add(node);
        orderedTasks.add(task);
    }

}
