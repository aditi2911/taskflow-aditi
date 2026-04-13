package com.taskflow.project;

import com.taskflow.user.User;
import com.taskflow.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public List<ProjectResponse> getAllProjects(UUID userId) {
        return projectRepository.findAllByUserInvolved(userId)
                .stream()
                .map(ProjectResponse::from)
                .toList();
    }

    public ProjectResponse createProject(ProjectRequest request, UUID userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(owner);

        return ProjectResponse.from(projectRepository.save(project));
    }

    public ProjectResponse getProject(UUID projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));
        return ProjectResponse.from(project);
    }

    public ProjectResponse updateProject(UUID projectId, ProjectRequest request, UUID userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!project.getOwner().getId().equals(userId)) {
            throw new SecurityException("forbidden");
        }

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return ProjectResponse.from(projectRepository.save(project));
    }

    public void deleteProject(UUID projectId, UUID userId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        if (!project.getOwner().getId().equals(userId)) {
            throw new SecurityException("forbidden");
        }

        projectRepository.delete(project);
    }
}