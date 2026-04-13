package com.taskflow.project;

import com.taskflow.user.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.getAllProjects(user.getId()));
    }

    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(
            @Valid @RequestBody ProjectRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(projectService.createProject(request, user.getId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProject(@PathVariable UUID id) {
        return ResponseEntity.ok(projectService.getProject(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable UUID id,
            @Valid @RequestBody ProjectRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(projectService.updateProject(id, request, user.getId()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        projectService.deleteProject(id, user.getId());
        return ResponseEntity.noContent().build();
    }
}