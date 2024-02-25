package com.nhnacademy.minidooray.gateway.adaptor.task;

import com.nhnacademy.minidooray.gateway.domain.task.*;

import java.util.List;

public interface TaskProjectAdaptor {

    List<ProjectDto> getProjectListByUserId(String id);

    List<ProjectMemberRequest> getProjectMemberList(String projectId);

    Project getProjectById(String id);

    Project saveProject(ProjectRequest projectRequest);

    ProjectDto saveProjectMember(int projectId, ProjectMemberRequest projectMemberRequest);

    List<TagResponse> getProjectTagList(String projectId);

    TagResponse saveProjectTag(TagRequest tagRequest, String projectId);

    MilestoneResponse saveProjectMilestone(MilestoneRequest milestoneRequest, String projectId);

    List<MilestoneResponse> getMilestoneListByProjectId(String projectId);

}
