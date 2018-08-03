package org.aplatanao.billing.endpoint;

import org.aplatanao.billing.Converter;
import org.aplatanao.billing.rest.api.TasksApi;
import org.aplatanao.billing.rest.model.Task;
import org.aplatanao.billing.rest.model.Tasks;
import org.camunda.bpm.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TasksEndpoint implements TasksApi {

    private TaskService taskService;

    private Converter converter;

    @Autowired
    public TasksEndpoint(TaskService taskService, Converter converter) {
        this.taskService = taskService;
        this.converter = converter;
    }

    @Override
    public Tasks getTasks() {
        Set<Task> collect = taskService.createTaskQuery().list().stream().map(t -> new Task()
                .uuid(t.getId())
                .name(t.getName())
                .key(t.getTaskDefinitionKey())
                .priority(t.getPriority())
                .dueDate(converter.toLocalDate(t.getDueDate()))
                .description(t.getDescription())
        ).collect(Collectors.toSet());

        Tasks tasks = new Tasks();
        tasks.addAll(collect);
        return tasks;
    }
}
