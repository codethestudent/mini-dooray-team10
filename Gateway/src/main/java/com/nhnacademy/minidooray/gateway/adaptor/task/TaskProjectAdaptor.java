package com.nhnacademy.minidooray.gateway.adaptor.task;

import com.nhnacademy.minidooray.gateway.domain.task.Project;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectDto;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectMemberRequest;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectRequest;

import java.util.List;

public interface TaskProjectAdaptor {

    List<ProjectDto> getProjectListByUserId(String id);

    List<ProjectMemberRequest> getProjectMemberList(String projectId);

    Project getProjectById(String id);

    Project saveProject(ProjectRequest projectRequest);

    ProjectDto saveProjectMember(int projectId, ProjectMemberRequest projectMemberRequest);

}
