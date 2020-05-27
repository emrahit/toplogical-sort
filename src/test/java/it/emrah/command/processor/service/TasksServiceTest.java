package it.emrah.command.processor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.emrah.command.processor.model.Task;
import it.emrah.command.processor.model.rest.TaskRequest;
import it.emrah.command.processor.model.rest.TaskRequestEntity;

class TasksServiceTest {

    private TasksService service;

    @BeforeEach
    void setUp() {
        service = new TasksService();
    }

    @Test
    public void buildTaskExecutionOrder() {
        List<TaskRequestEntity> tasks = new ArrayList<>();
        tasks.add(new TaskRequestEntity("task-1", "touch /tmp/file1\"", null));
        tasks.add(new TaskRequestEntity("task-2", "cat /tmp/file1", Arrays.asList("task-3")));
        tasks.add(new TaskRequestEntity("task-3", "echo 'Hello World!' > /tmp/file1", Arrays.asList("task-1")));
        tasks.add(new TaskRequestEntity("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3")));

        TaskRequest taskRequest = new TaskRequest(tasks);
        List<Task> result = service.buildTaskExecutionOrder(taskRequest);
        assertEquals(result.get(0).getName(), "task-1");
        assertEquals(result.get(1).getName(), "task-3");
        assertEquals(result.get(2).getName(), "task-2");
        assertEquals(result.get(3).getName(), "task-4");
    }

    @Test
    public void buildTaskExecutionOrderCycledDependency() {
        List<TaskRequestEntity> tasks = new ArrayList<>();
        tasks.add(new TaskRequestEntity("task-1", "touch /tmp/file1\"", new ArrayList<>()));
        tasks.add(new TaskRequestEntity("task-2", "cat /tmp/file1", Arrays.asList("task-3", "task-4")));
        tasks.add(new TaskRequestEntity("task-3", "echo 'Hello World!' > /tmp/file1", Arrays.asList("task-1")));
        tasks.add(new TaskRequestEntity("task-4", "rm /tmp/file1", Arrays.asList("task-2", "task-3")));

        TaskRequest taskRequest = new TaskRequest(tasks);
        Exception exception = assertThrows(RuntimeException.class, () -> {
            List<Task> result = service.buildTaskExecutionOrder(taskRequest);
        });

        assertEquals("400 BAD_REQUEST \"Tasks Cannot be empty or null\"", exception.getMessage() );
    }
}