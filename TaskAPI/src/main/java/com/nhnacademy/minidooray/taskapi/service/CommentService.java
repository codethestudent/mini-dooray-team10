package com.nhnacademy.minidooray.taskapi.service;

import com.nhnacademy.minidooray.taskapi.domain.CommentDto;
import com.nhnacademy.minidooray.taskapi.entity.Comment;
import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import com.nhnacademy.minidooray.taskapi.entity.Task;
import com.nhnacademy.minidooray.taskapi.repository.CommentRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectMemberRepository;
import com.nhnacademy.minidooray.taskapi.repository.ProjectRepository;
import com.nhnacademy.minidooray.taskapi.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public List<CommentDto> getComments(int taskId) {
        List<Comment> comments = commentRepository.findByTask_TaskId(taskId);
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment, commentDto);
            commentDto.setTaskId(taskId);
            commentDtos.add(commentDto);
        }
        return commentDtos;
    }

    public CommentDto getComment(int taskId, int commentId) {
        Optional<Comment> comment = commentRepository.findByCommentIdAndTask_TaskId(commentId, taskId);
        Optional<Task> task = taskRepository.findById(taskId);

        if (comment.isEmpty() || task.isEmpty()) {
            throw new EntityNotFoundException("Comment commentId " + commentId + " not found");
        }
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment.get(), commentDto);
        commentDto.setTaskId(taskId);
        commentDto.setProjectId(task.get().getProject().getProjectId());
        return commentDto;
    }

    public CommentDto createComment(int taskId, CommentDto commentDto) {
        Optional<Task> task = taskRepository.findById(taskId);

        if (task.isEmpty()) {
            throw new EntityNotFoundException("task " + taskId + " not found");
        }
        int projectId = task.get().getProject().getProjectId();
        if (projectId != commentDto.getProjectId()) {
            throw new EntityNotFoundException("comment entity not found in project : " + commentDto.getProjectId());
        }
        Comment comment = commentRepository.save(new Comment(
                commentDto.getContent(),
                commentDto.getCreatedDate(),
                task.get(),
                commentDto.getUserId()
        ));
        BeanUtils.copyProperties(comment, commentDto);
        return commentDto;
    }

    public CommentDto updateComment(int taskId, int commentId, CommentDto commentDto) {
        Optional<Comment> commentOpt = commentRepository.findByCommentIdAndTask_TaskId(commentId, taskId);
        Optional<Task> task = taskRepository.findByTaskIdAndProject_ProjectId(taskId, commentDto.getProjectId());

        if (commentOpt.isEmpty() || task.isEmpty()) {
            throw new EntityNotFoundException("comment commentId : " + commentId + " not found");
        }
        commentOpt.get().setContent(commentDto.getContent());
        commentOpt.get().setCreatedDate(commentDto.getCreatedDate());
        commentOpt.get().setUserId(commentDto.getUserId());
        commentOpt.get().setTask(task.get());
        CommentDto commentDto1 = new CommentDto();
        BeanUtils.copyProperties(commentOpt.get(), commentDto1);
        commentDto1.setTaskId(taskId);
        commentDto1.setProjectId(task.get().getProject().getProjectId());
        return commentDto1;
    }

    public ResponseEntity<String> deleteComment(int taskId, int id) {
        Optional<Comment> comment = commentRepository.findByCommentIdAndTask_TaskId(id, taskId);
        if (comment.isPresent()) {
            commentRepository.deleteById(id);
            return ResponseEntity.ok().body("ok");
        }
        return ResponseEntity.badRequest().build();
    }
}
