package com.taskflow.project;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private UUID ownerId;
    private LocalDateTime createdAt;

    public static ProjectResponse from(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setName(project.getName());
        response.setDescription(project.getDescription());
        response.setOwnerId(project.getOwner().getId());
        response.setCreatedAt(project.getCreatedAt());
        return response;
    }
}