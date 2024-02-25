package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.Project;
import lombok.Data;

@Data
public class ProjectDto {
    private int projectId;
    private String projectName;

    public ProjectDto(Project project) {
        this.projectId = project.getProjectId();
        this.projectName = project.getProjectName();
    }
}
