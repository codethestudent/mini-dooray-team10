package com.nhnacademy.minidooray.taskapi.repository;

import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTagRepository extends JpaRepository<TaskTag, TaskTag.Pk> {
}
