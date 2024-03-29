package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    public List<ProjectMemberDto> getProjectMembers(int projectId) {
        List<ProjectMember> members = projectMemberRepository.getProjectMembersByProject_ProjectId(projectId);
        return members.stream()
                .map(ProjectMemberDto::new)
                .collect(Collectors.toList());
    }

    public List<ProjectDto> getProjects(String userId) {
        List<ProjectMember> projectMembers = projectMemberRepository.findByPkUserId(userId);

        List<Project> projects = projectMembers.stream()
                .map(ProjectMember::getProject)
                .distinct()
                .toList();

        return projects.stream()
                .map(ProjectDto::new)
                .collect(Collectors.toList());
    }

    public ProjectMember getProjectMember(String userId, int projectId) {
        ProjectMember.Pk pk = new ProjectMember.Pk(userId, projectId);
        Optional<ProjectMember> projectMember = projectMemberRepository.findById(pk);
        if (projectMember.isEmpty()) {
            throw new EntityNotFoundException("Project " + projectId + ", Member id " + userId + " not found");
        }
        return projectMember.get();
    }

    public ProjectMemberDto createProjectMember(int projectId, ProjectMemberDto projectMemberDto) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("Project id: " + projectId + " not found");
        }
        ProjectMember.Pk pk = new ProjectMember.Pk(projectMemberDto.getUserId(), projectId);
        ProjectMember projectMember = new ProjectMember(pk, project.get());

        if (projectMemberRepository.existsByPkUserIdAndPkProjectId(projectMemberDto.getUserId(), projectId)) {
            throw new EntityExistsException(projectMember.getPk().getUserId() + ", " + projectId + " already exists");
        }
        ProjectMember projectMember1 = projectMemberRepository.save(projectMember);
        ProjectMemberDto projectMemberDto1 = new ProjectMemberDto();
        projectMemberDto1.setUserId(projectMember1.getPk().getUserId());
        projectMemberDto1.setProjectId(projectMember1.getProject().getProjectId());
        return projectMemberDto1;
    }

    public ProjectMember updateProjectMember(ProjectMember projectMember) {
        Optional<ProjectMember> projectMemberOpt = projectMemberRepository.findById(projectMember.getPk());
        if (projectMemberOpt.isEmpty()) {
            throw new EntityNotFoundException(projectMember.getPk() + " Not Found");
        }
        return projectMemberRepository.save(projectMember);
    }

    public ResponseEntity<String> deleteProjectMember(int projectId, String userId) {
        ProjectMember.Pk pk = new ProjectMember.Pk(userId, projectId);
        if (projectMemberRepository.existsById(pk)) {
            projectMemberRepository.deleteById(pk);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
