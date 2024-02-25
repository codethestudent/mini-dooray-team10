package com.nhnacademy.minidooray.gateway.service.task;

import com.nhnacademy.minidooray.gateway.domain.task.*;

import java.util.List;

public interface TaskProjectService {

    List<ProjectDto> getListProjectDto(String id);

    List<ProjectMemberRequest> getListMemberOfProject(String projectId);

    Project getSingleProject(String id);

    Project saveSingleProject(ProjectRequest projectRequest);

    ProjectDto saveSingleProjectMember(int projectId, ProjectMemberRequest projectMemberRequest);

    List<TagResponse> getListTag(String projectId);

    TagResponse saveSingleProjectTag(TagRequest tagRequest,String projectId);

    MilestoneResponse saveSingleMilestone(MilestoneRequest milestoneRequest, String projectId);

    List<MilestoneResponse> getListMilestoneByProjectId(String projectId);

}
