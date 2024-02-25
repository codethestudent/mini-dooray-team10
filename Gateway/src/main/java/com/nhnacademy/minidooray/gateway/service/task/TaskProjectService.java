package com.nhnacademy.minidooray.gateway.service.task;

import com.nhnacademy.minidooray.gateway.domain.task.Project;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectDto;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectMemberRequest;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectRequest;

import java.util.List;

public interface TaskProjectService {

    List<ProjectDto> getListProjectDto(String id);

    List<ProjectMemberRequest> getListMemberOfProject(String projectId);

    Project getSingleProject(String id);

    Project saveSingleProject(ProjectRequest projectRequest);

    ProjectDto saveSingleProjectMember(int projectId, ProjectMemberRequest projectMemberRequest);

}
