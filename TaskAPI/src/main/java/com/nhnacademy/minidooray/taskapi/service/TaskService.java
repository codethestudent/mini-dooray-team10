package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TaskDto;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.repository.MilestoneRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final MilestoneRepository milestoneRepository;

    public List<Task> getTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByProjectId(int projectId) {
        return taskRepository.findAllByProject_ProjectId(projectId);
    }

    public Task getTask(int projectId, int id) {
        Optional<Task> task = taskRepository.findByTaskIdAndProject_ProjectId(id, projectId);
        if (task.isEmpty()) {
            throw new EntityNotFoundException("Project Id : " + projectId + "Task id : " + id + " not found");
        }
        return task.get();
    }

    public Task createTaskByProjectId(int projectId, TaskDto taskDto) {
        Optional<Project> project = projectRepository.findById(projectId);
        Optional<Milestone> milestone = milestoneRepository.findByMilestoneIdAndProject_ProjectId(taskDto.getMilestoneId(), projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("from task request project " + projectId + " not found");
        }
        Task task = new Task(
                1,
                taskDto.getTaskName(),
                taskDto.getDeadLine(),
                taskDto.getContent(),
                project.get(),
                null,
                taskDto.getUserId()
        );
        milestone.ifPresent(task::setMilestone);
        return taskRepository.save(task);
    }

    @Transactional
    public Task updateTask(int projectId, int id, TaskDto taskDto) {
        Optional<Task> taskOpt = taskRepository.findByTaskIdAndProject_ProjectId(id, projectId);
        Optional<Milestone> milestone = milestoneRepository.findByMilestoneIdAndProject_ProjectId(taskDto.getMilestoneId(), projectId);
        Optional<Project> project = projectRepository.findById(taskDto.getProjectId());

        if (taskOpt.isEmpty() || project.isEmpty()) {
            throw new EntityNotFoundException("Task " + id + " Not Found");
        }
        taskOpt.get().setTaskName(taskDto.getTaskName());
        taskOpt.get().setDeadLine(taskDto.getDeadLine());
        taskOpt.get().setContent(taskDto.getContent());
        taskOpt.get().setUserId(taskDto.getUserId());
        taskOpt.get().setProject(project.get());
        if (milestone.isEmpty()) {
            taskOpt.get().setMilestone(null);
        } else {
            taskOpt.get().setMilestone(milestone.get());
        }
        return taskRepository.save(taskOpt.get());
    }

    public ResponseEntity<String> deleteTask(int projectId, int id) {
        Optional<Task> task = taskRepository.findByTaskIdAndProject_ProjectId(id, projectId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
