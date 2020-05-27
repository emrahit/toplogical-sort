package it.emrah.command.processor.service;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import it.emrah.command.processor.model.Task;

public class TaskDependencyValidator {

    private Set<String> isVisited = new HashSet<>();
    private Set<String> processed = new HashSet<>();
    private boolean isCycled;


    private TaskDependencyValidator(Map<String, Task> tasks) {
        for (var task : tasks.keySet()) {
            if (this.processed.contains(task)) {
                continue;
            }
            checkForCycleDependency(task, tasks);
            if (isCycled) {
                break;
            }
        }
    }

    private void checkForCycleDependency(String task, Map<String, Task> tasks) {
        processed.add(task);
        isVisited.add(task);
        var currentTask = tasks.get(task);
        if (currentTask.getRequires() != null) {
            for (var nextTask : currentTask.getRequires()) {
                if (isCycled) {
                    return;
                } else if (!isVisited.contains(nextTask)) {
                    checkForCycleDependency(nextTask, tasks);
                } else if (processed.contains(nextTask)) {
                    this.isCycled = true;
                }
            }
        }

        processed.remove(task);
    }

    public static boolean isCycleDependency(Map<String, Task> tasks) {
        var validator = new TaskDependencyValidator(tasks);
        return validator.isCycled;
    }
}
