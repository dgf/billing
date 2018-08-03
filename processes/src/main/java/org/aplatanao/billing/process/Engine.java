package org.aplatanao.billing.process;

import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.engine.variable.Variables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class Engine {

    private RuntimeService runtimeService;

    private TaskService taskService;

    private HistoryService historyService;

    @Autowired
    public Engine(RuntimeService runtimeService, TaskService taskService, HistoryService historyService) {
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.historyService = historyService;
    }

    public ProcessInstance start(String message, String variable, Object value) {
        return runtimeService.startProcessInstanceByMessage(message,
                new HashMap<String, Object>() {
                    {
                        put(variable, Variables.objectValue(value).create());
                    }
                }
        );
    }

    public Task getTask(ProcessInstance instance) {
        return taskService.createTaskQuery().processInstanceId(instance.getId()).singleResult();
    }

    public Task getTask(String uuid) {
        return taskService.createTaskQuery().taskId(uuid).singleResult();
    }

    public void complete(Task task, String variable, Boolean value) {
        runtimeService.setVariable(task.getProcessInstanceId(), variable, Variables.booleanValue(value));
        taskService.complete(task.getId());
    }

    public <V> V getVariable(Class<V> clazz, String executionId, String name) {
        try {
            return clazz.cast(runtimeService.getVariable(executionId, name));
        } catch (ClassCastException e) {
            return null;
        }
    }

    public <V> V getHistory(Class<V> clazz, String instanceId, String name) {
        return clazz.cast(historyService.createHistoricVariableInstanceQuery()
                .processInstanceId(instanceId)
                .variableName(name).singleResult().getValue()
        );
    }
}
