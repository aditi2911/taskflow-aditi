package com.taskflow.task;

import com.taskflow.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/projects/{projectId}/tasks")
    public ResponseEntity<List<TaskResponse>> getTasks(
            @PathVariable UUID projectId,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) UUID assigneeId) {
        return ResponseEntity.ok(taskService.getTasks(projectId, status, assigneeId));
    }

    @PostMapping("/projects/{projectId}/tasks")
    public ResponseEntity<TaskResponse> createTask(
            @PathVariable UUID projectId,
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(taskService.createTask(projectId, request, user.getId()));
    }

    @PatchMapping("/tasks/{taskId}")
    public ResponseEntity<TaskResponse> updateTask(
            @PathVariable UUID taskId,
            @RequestBody TaskRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.updateTask(taskId, request, user.getId()));
    }

    @DeleteMapping("/tasks/{taskId}")
    public ResponseEntity<Void> deleteTask(
            @PathVariable UUID taskId,
            @AuthenticationPrincipal User user) {
        taskService.deleteTask(taskId, user.getId());
        return ResponseEntity.noContent().build();
    }
}