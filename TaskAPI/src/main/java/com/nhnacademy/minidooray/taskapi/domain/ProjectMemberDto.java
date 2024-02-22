package com.nhnacademy.minidooray.taskapi.domain;

import com.nhnacademy.minidooray.taskapi.entity.ProjectMember;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProjectMemberDto {
    private String userId;
    private int projectId;

    public ProjectMemberDto(ProjectMember projectMember) {
        this.userId = projectMember.getPk().getUserId();
        this.projectId = projectMember.getPk().getProjectId();
    }
}
