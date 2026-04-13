package com.taskflow.task;

import com.taskflow.project.Project;
import com.taskflow.project.ProjectRepository;
import com.taskflow.user.User;
import com.taskflow.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository,
                       ProjectRepository projectRepository,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<TaskResponse> getTasks(UUID projectId, String status, UUID assigneeId) {
        TaskStatus taskStatus = null;
        if (status != null) {
            taskStatus = TaskStatus.valueOf(status);
        }

        return taskRepository.findByProjectIdWithFilters(projectId, taskStatus, assigneeId)
                .stream()
                .map(TaskResponse::from)
                .toList();
    }

    public TaskResponse createTask(UUID projectId, TaskRequest request, UUID userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setProject(project);

        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new IllegalArgumentException("Assignee not found"));
            task.setAssignee(assignee);
        }

        return TaskResponse.from(taskRepository.save(task));
    }

    public TaskResponse updateTask(UUID taskId, TaskRequest request, UUID userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        if (request.getTitle() != null) task.setTitle(request.getTitle());
        if (request.getDescription() != null) task.setDescription(request.getDescription());
        if (request.getStatus() != null) task.setStatus(request.getStatus());
        if (request.getPriority() != null) task.setPriority(request.getPriority());
        if (request.getDueDate() != null) task.setDueDate(request.getDueDate());

        if (request.getAssigneeId() != null) {
            User assignee = userRepository.findById(request.getAssigneeId())
                    .orElseThrow(() -> new IllegalArgumentException("Assignee not found"));
            task.setAssignee(assignee);
        }

        return TaskResponse.from(taskRepository.save(task));
    }

    public void deleteTask(UUID taskId, UUID userId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found"));

        UUID ownerId = task.getProject().getOwner().getId();

        if (!ownerId.equals(userId)) {
            throw new SecurityException("forbidden");
        }

        taskRepository.delete(task);
    }
}