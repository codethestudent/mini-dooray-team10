package com.nhnacademy.minidooray.taskapi.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskDto {
    private String taskName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deadLine;
    private String content;
    private int projectId;
    private int milestoneId;
    private String userId;
}
