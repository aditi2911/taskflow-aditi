package com.taskflow.task;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public class TaskRequest {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private UUID assigneeId;
    private LocalDate dueDate;

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public UUID getAssigneeId() { return assigneeId; }
    public LocalDate getDueDate() { return dueDate; }

    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public void setPriority(TaskPriority priority) { this.priority = priority; }
    public void setAssigneeId(UUID assigneeId) { this.assigneeId = assigneeId; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
}