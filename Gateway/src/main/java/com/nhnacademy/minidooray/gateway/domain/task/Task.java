package com.nhnacademy.minidooray.gateway.domain.task;

import java.time.LocalDateTime;

public class Task {
    private int taskId;
    private String taskName;
    private LocalDateTime deadLine;
    private String content;
    private MilestoneResponse milestoneResponse;

}
