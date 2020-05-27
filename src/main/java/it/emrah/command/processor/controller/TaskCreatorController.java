package it.emrah.command.processor.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import it.emrah.command.processor.controller.validator.RequestValidator;
import it.emrah.command.processor.model.rest.TaskRequest;
import it.emrah.command.processor.model.rest.TaskResponse;
import it.emrah.command.processor.service.TasksService;

@RestController("/")
public class TaskCreatorController {


    private TasksService tasksService;

    @Autowired
    public TaskCreatorController(TasksService tasksService) {
        this.tasksService = tasksService;
    }

    @PostMapping
    public ResponseEntity<List<TaskResponse>> processTasks(@RequestBody TaskRequest taskRequest) {
        RequestValidator.validate(taskRequest);
        List<TaskResponse> result = this.tasksService.getOrderedTasksAsList(taskRequest);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }

    @PostMapping("/bash")
    public ResponseEntity<String> processTasksAsBash(@RequestBody TaskRequest taskRequest) {
        RequestValidator.validate(taskRequest);
        String result = this.tasksService.getOrderedTasksAsExecutableBash(taskRequest);
        return new ResponseEntity<>(result, HttpStatus.ACCEPTED);
    }
}
