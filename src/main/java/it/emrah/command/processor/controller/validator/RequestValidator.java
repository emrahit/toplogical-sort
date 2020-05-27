package it.emrah.command.processor.controller.validator;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import it.emrah.command.processor.model.rest.TaskRequest;

public class RequestValidator {

    public static void validate(TaskRequest taskRequest) {
        if (taskRequest.getTasks() == null || taskRequest.getTasks().isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Tasks Cannot be empty or null");
        }
        for (var task : taskRequest.getTasks()) {
            if(task.getName() == null || task.getName().isEmpty()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Name cannot be empty or null");
            }

            if(task.getCommand() == null || task.getCommand().isEmpty()){
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "Name cannot be empty or null");
            }
        }
    }

}
