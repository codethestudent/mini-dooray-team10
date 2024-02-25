package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comment")
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "content")
    private String content;

    @Column(name = "created_date")
    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @Column(name = "user_id")
    private String userId;

    public Comment() {
    }

    public Comment(String content, LocalDateTime createdDate, Task task, String userId) {
        this.content = content;
        this.createdDate = createdDate;
        this.task = task;
        this.userId = userId;
    }
}
