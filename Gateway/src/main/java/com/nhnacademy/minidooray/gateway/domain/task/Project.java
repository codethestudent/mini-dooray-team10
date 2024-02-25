package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;

import java.util.List;

@Getter
public class Project {

    private int projectId;

    private String projectName;

    private String projectDescription;

    private String adminId;

    private ProjectState projectState;

    private List<Task> tasks;

}
