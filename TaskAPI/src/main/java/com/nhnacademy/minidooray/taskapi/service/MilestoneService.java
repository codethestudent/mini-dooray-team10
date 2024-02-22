package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MilestoneService {
    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;

    public List<Milestone> getMilestones(int projectId) {
        return milestoneRepository.findAllByProject_ProjectId(projectId);
    }

    public Milestone getMilestone(int projectId, int id) {
        Optional<Milestone> milestone = milestoneRepository.findByMilestoneIdAndProject_ProjectId(id, projectId);
        if (milestone.isEmpty()) {
            throw new EntityNotFoundException("Milestone id " + id + " not found");
        }
        return milestone.get();
    }

    public Milestone createMilestone(int projectId, Milestone milestone) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("project id : " + projectId + " not found");
        }
        milestone.setProject(project.get());

        return milestoneRepository.save(milestone);
    }

    public Milestone updateMilestone(int projectId, int id, MilestoneDto milestoneDto) {
        Optional<Milestone> milestoneOpt = milestoneRepository.findByMilestoneIdAndProject_ProjectId(id, projectId);
        Optional<Project> project = projectRepository.findById(milestoneDto.getProjectId());

        if (milestoneOpt.isEmpty() || project.isEmpty()) {
            throw new EntityNotFoundException(id + ", " + projectId + " Not Found");
        }
        milestoneOpt.get().setMilestoneName(milestoneDto.getMilestoneName());
        milestoneOpt.get().setStartDate(milestoneDto.getStartDate());
        milestoneOpt.get().setEndDate(milestoneDto.getEndDate());
        milestoneOpt.get().setProject(project.get());

        return milestoneRepository.save(milestoneOpt.get());
    }

    @Transactional
    public ResponseEntity<String> deleteMilestone(int projectId, int id) {
        List<Task> tasks = taskRepository.findByMilestone_MilestoneId(id);
        Optional<Milestone> milestoneOpt = milestoneRepository.findByMilestoneIdAndProject_ProjectId(id, projectId);
        if (milestoneOpt.isPresent()) {
            for (Task task : tasks) {
                task.setMilestone(null);
                taskRepository.save(task);
            }
            milestoneRepository.deleteById(id);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
