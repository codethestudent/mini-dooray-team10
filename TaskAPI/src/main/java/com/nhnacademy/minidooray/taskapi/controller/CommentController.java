package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.CommentDto;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import com.nhnacademy.minidooray.taskapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task/{taskId}/comment")
public class CommentController {
    private final CommentService commentService;

    @GetMapping
    public List<CommentDto> getComments(@PathVariable int taskId) {
        return commentService.getComments(taskId);
    }

    @GetMapping("/{id}")
    public CommentDto getComment(@PathVariable int taskId,
                                 @PathVariable int id) {
        return commentService.getComment(taskId, id);
    }

    @PostMapping
    public CommentDto createComment(@PathVariable int taskId,
                                    @RequestBody CommentDto commentDto) {
        return commentService.createComment(taskId, commentDto);
    }

    @PutMapping("/{id}")
    public CommentDto updateComment(@PathVariable int taskId,
                                    @PathVariable int id,
                                    @RequestBody CommentDto commentDto) {
        return commentService.updateComment(taskId, id, commentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int taskId,
                                                @PathVariable int id) {
        return commentService.deleteComment(taskId, id);
    }
}
