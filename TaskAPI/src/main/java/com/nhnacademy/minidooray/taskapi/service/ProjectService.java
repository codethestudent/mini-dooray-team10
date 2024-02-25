package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;

    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    public Project getProject(int id) {
        Optional<Project> project = projectRepository.findById(id);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("Project id " + id + " not found");
        }
        return project.get();
    }

    public Project createProject(Project project) {
        Project savedProject = projectRepository.save(project);
        String adminId = savedProject.getAdminId();
        projectMemberService.createProjectMember(project.getProjectId(), new ProjectMemberDto(adminId, project.getProjectId()));
        return savedProject;
    }

    public Project updateProject(int id, Project project) {
        Optional<Project> projectOpt = projectRepository.findById(id);
        if (projectOpt.isEmpty()) {
            throw new EntityNotFoundException(project.getProjectId() + " Not Found");
        }
        project.setProjectId(id);
        return projectRepository.save(project);
    }

    public ResponseEntity<String> deleteProject(int id) {
        if (projectRepository.existsById(id)) {
            projectRepository.deleteById(id);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
