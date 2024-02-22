package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.entity.Tag;
import com.nhnacademy.minidooray.taskapi.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/{project_id}/tag")
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping
    public List<Tag> getTags(@PathVariable int project_id) {
        return tagService.getTags(project_id);
    }

    @GetMapping("/{id}")
    public Tag getTag(@PathVariable int project_id,
                      @PathVariable int id) {
        return tagService.getTag(project_id, id);
    }

    @PostMapping
    public Tag createTag(@PathVariable int project_id,
                         @RequestBody Tag tag) {
        return tagService.createTag(project_id, tag);
    }

    @PutMapping("/{id}")
    public Tag updateTag(@PathVariable int project_id,
                         @PathVariable int id,
                         @RequestBody Tag tag) {
        return tagService.updateTag(project_id, id, tag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable int project_id,
                                            @PathVariable int id) {
        return tagService.deleteTag(project_id, id);
    }
}
