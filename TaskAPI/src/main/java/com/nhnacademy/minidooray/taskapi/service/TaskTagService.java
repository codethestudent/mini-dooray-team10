package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.entity.TaskTag;
import com.nhnacademy.minidooray.taskapi.repository.TaskTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TaskTagService {
    private final TaskTagRepository taskTagRepository;

    public List<TaskTag> getProjectMembers() {
        return taskTagRepository.findAll();
    }

    public TaskTag getProjectMember(int tagId, int taskId) {
        TaskTag.Pk pk = new TaskTag.Pk(tagId, taskId);
        Optional<TaskTag> taskTag = taskTagRepository.findById(pk);
        if (taskTag.isEmpty()) {
            throw new EntityNotFoundException("Project " + taskId + ", Member id " + tagId + " not found");
        }
        return taskTag.get();
    }

    public TaskTag createProjectMember(TaskTag taskTag) {
        if (taskTagRepository.existsById(taskTag.getPk())) {
            throw new EntityExistsException(taskTag.getPk() + " already exists");
        }
        return taskTagRepository.save(taskTag);
    }

    public TaskTag updateProjectMember(TaskTag taskTag) {
        Optional<TaskTag> taskTagOpt = taskTagRepository.findById(taskTag.getPk());
        if (taskTagOpt.isEmpty()) {
            throw new EntityNotFoundException(taskTag.getPk() + " Not Found");
        }
        return taskTagRepository.save(taskTag);
    }

    public ResponseEntity<String> deleteProjectMember(int tagId, int taskId) {
        TaskTag.Pk pk = new TaskTag.Pk(tagId, taskId);
        if (taskTagRepository.existsById(pk)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
