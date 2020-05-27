package it.emrah.command.processor.model.rest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class TaskResponse {
    private String name;
    private String command;
}
