package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MilestoneRepository extends JpaRepository<Milestone, Integer> {
    List<Milestone> findAllByProject_ProjectId(int projectId);

    Optional<Milestone> findByMilestoneIdAndProject_ProjectId(int milestoneId, int projectId);

}
