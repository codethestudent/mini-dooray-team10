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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
//    private final ProjectMemberRepository projectMemberRepository;

    public List<Comment> getComments(int taskId) {
        return commentRepository.findByTask_TaskId(taskId);
    }

    public Comment getComment(int taskId, int id) {
        Optional<Comment> comment = commentRepository.findByCommentIdAndTask_TaskId(id, taskId);
        if (comment.isEmpty()) {
            throw new EntityNotFoundException("Comment id " + id + " not found");
        }
        return comment.get();
    }

    public Comment createComment(int taskId, CommentDto commentDto) {
        ProjectMember.Pk pk = new ProjectMember.Pk(commentDto.getUserId(), commentDto.getProjectId());
        Optional<Task> task = taskRepository.findById(taskId);
//        Optional<ProjectMember> projectMember = projectMemberRepository.findById(pk);

        if (task.isEmpty() /*|| projectMember.isEmpty()*/) {
            throw new EntityNotFoundException("task " + taskId + " or projectMember " + pk + " not found");
        }
        if (taskRepository.findById(commentDto.getTaskId()).get().getProject().getProjectId() != commentDto.getProjectId()) {
            throw new EntityNotFoundException("comment entity not found in project : " + commentDto.getProjectId());
        }

        Comment comment = new Comment(
                1,
                commentDto.getContent(),
                commentDto.getCreatedDate(),
                task.get()
        );

        return commentRepository.save(comment);
    }

    public Comment updateComment(int taskId, int id, CommentDto commentDto) {
//        ProjectMember.Pk pk = new ProjectMember.Pk(commentDto.getUserId(), commentDto.getProjectId());

        Optional<Comment> commentOpt = commentRepository.findByCommentIdAndTask_TaskId(id, taskId);
//        Optional<ProjectMember> projectMember = projectMemberRepository.findById(pk);
        Optional<Task> task = taskRepository.findById(commentDto.getTaskId());

        if (commentOpt.isEmpty() /*|| projectMember.isEmpty()*/ || task.isEmpty()) {
            throw new EntityNotFoundException("comment id : " + id + " not found");
        }
        commentOpt.get().setContent(commentDto.getContent());
        commentOpt.get().setCreatedDate(commentDto.getCreatedDate());
//        commentOpt.get().setProjectMember(projectMember.get());
        commentOpt.get().setTask(task.get());
        return commentRepository.save(commentOpt.get());
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
