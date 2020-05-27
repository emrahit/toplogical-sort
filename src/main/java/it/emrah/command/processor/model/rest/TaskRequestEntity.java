package it.emrah.command.processor.model.rest;

import java.util.List;

import javax.validation.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TaskRequestEntity {
    private String name;
    private String command;
    private List<String> requires;
}
