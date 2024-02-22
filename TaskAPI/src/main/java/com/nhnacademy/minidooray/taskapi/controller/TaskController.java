package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project/{projectId}/task")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public List<Task> getTasks(@PathVariable int projectId) {
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable int projectId,
                        @PathVariable int id) {
        return taskService.getTask(projectId, id);
    }

    @PostMapping
    public Task createTask(@PathVariable int projectId,
                           @RequestBody TaskDto taskDto) {
        return taskService.createTaskByProjectId(projectId, taskDto);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable int projectId,
                           @PathVariable int id,
                           @RequestBody TaskDto taskDto) {
        return taskService.updateTask(projectId, id, taskDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int projectId,
                                             @PathVariable int id) {
        return taskService.deleteTask(projectId, id);
    }
}
