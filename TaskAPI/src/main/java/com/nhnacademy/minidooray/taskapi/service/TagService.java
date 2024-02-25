package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.TagDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final ProjectRepository projectRepository;

    public List<TagDto> getTags(int projectId) {
        List<TagDto> tagDtos = new ArrayList<>();
        List<Tag> tags = tagRepository.findAllByProject_ProjectId(projectId);
        for (Tag tag : tags) {
            tagDtos.add(new TagDto(tag.getTagId(), tag.getTagName()));
        }
        return tagDtos;
    }

    public TagDto getTag(int projectId, int id) {
        Optional<Tag> tag = tagRepository.findByTagIdAndProject_ProjectId(id, projectId);
        if (tag.isEmpty()) {
            throw new EntityNotFoundException("Tag id " + id + " not found");
        }
        return new TagDto(tag.get().getTagId(), tag.get().getTagName());
    }

    public TagDto createTag(int projectId, TagDto tagDto) {
        Optional<Project> project = projectRepository.findById(projectId);
        if (project.isEmpty()) {
            throw new EntityNotFoundException("project id : " + projectId + " not found");
        }
        Tag tag = tagRepository.save(new Tag(1, tagDto.getTagName(), project.get()));
        tagDto.setTagId(tag.getTagId());
        tagDto.setTagName(tag.getTagName());
        return tagDto;
    }

    public TagDto updateTag(int projectId, int id, TagDto tagDto) {
        Optional<Tag> tagOpt = tagRepository.findByTagIdAndProject_ProjectId(id, projectId);
        Optional<Project> project = projectRepository.findById(projectId);
        if (tagOpt.isEmpty() || project.isEmpty()) {
            throw new EntityNotFoundException(id + ", " + projectId + " Not Found");
        }
        tagOpt.get().setTagName(tagDto.getTagName());
        tagOpt.get().setProject(project.get());
        Tag tag = tagRepository.save(tagOpt.get());
        return new TagDto(tag.getTagId(), tag.getTagName());
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
