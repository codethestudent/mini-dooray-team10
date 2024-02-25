package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findAllByProject_ProjectId(int projectId);

    Optional<Task> findByTaskIdAndProject_ProjectId(int taskId, int projectId);

    List<Task> findByMilestone_MilestoneId(int milestoneId);

    Optional<Task> findByTaskId(int taskId);
}
