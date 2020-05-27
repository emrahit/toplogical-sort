package it.emrah.command.processor.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.emrah.command.processor.model.Task;
import it.emrah.command.processor.model.rest.TaskRequest;
import it.emrah.command.processor.model.rest.TaskResponse;

@Service
public class TasksService {

    @Value("${app.bashExecutable:#!/usr/bin/env bash}")
    private String bashExecutable;

    @Value("${app.lineSeparator:\n}")
    private String newLine;

    public List<TaskResponse> getOrderedTasksAsList(TaskRequest taskRequest) {
        var result = buildTaskExecutionOrder(taskRequest);
        return result.stream().map(x -> new TaskResponse(x.getName(), x.getCommand())).collect(Collectors.toList());
    }

    public String getOrderedTasksAsExecutableBash(TaskRequest taskRequest) {
        var result = buildTaskExecutionOrder(taskRequest);
        var sb = new StringBuilder();
        sb.append(bashExecutable);
        for (var current : result) {
            sb.append(newLine);
            sb.append(current.getCommand());
        }

        return sb.toString();
    }

    public List<Task> buildTaskExecutionOrder(TaskRequest taskRequest) {
        Map<String, Task> tasks = TaskConverter.convertTasks(taskRequest);
        // If there is cycled dependency, cannot create a ordered tasks
        if (TaskDependencyValidator.isCycleDependency(tasks)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tasks Cannot be empty or null");
        }
        TaskOrderer taskOrderer = new TaskOrderer(tasks);
        List<Task> ordered = taskOrderer.getOrderedTasks();
        return ordered;
    }

}
