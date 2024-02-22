package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.entity.Project;
import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public List<Tag> getTags(int projectId) {
        return tagRepository.findAllByProject_ProjectId(projectId);
    }

    public Tag getTag(int projectId, int id) {
        Optional<Tag> tag = tagRepository.findByTagIdAndProject_ProjectId(id, projectId);
        if (tag.isEmpty()) {
            throw new EntityNotFoundException("Tag id " + id + " not found");
        }
        return tag.get();
    }

    public Tag createTag(int projectId, Tag tag) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("project id : " + projectId + " not found");
        }
        tag.setProject(project.get());

        return tagRepository.save(tag);
    }

    public Tag updateTag(int projectId, int id, Tag tag) {
        Optional<Tag> tagOpt = tagRepository.findByTagIdAndProject_ProjectId(id, projectId);
        Optional<Project> project = projectRepository.findById(projectId);
        if (tagOpt.isEmpty() || project.isEmpty()) {
            throw new EntityNotFoundException(id + ", " + projectId + " Not Found");
        }
        tagOpt.get().setTagName(tag.getTagName());
        tagOpt.get().setProject(project.get());

        return tagRepository.save(tagOpt.get());
    }

    public ResponseEntity<String> deleteTag(int projectId, int id) {
        Optional<Tag> tagOpt = tagRepository.findByTagIdAndProject_ProjectId(id, projectId);
        if (tagOpt.isPresent()) {
            tagRepository.deleteById(id);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
