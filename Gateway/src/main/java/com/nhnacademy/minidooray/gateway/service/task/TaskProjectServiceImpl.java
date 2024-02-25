package com.nhnacademy.minidooray.gateway.service.task;

import com.nhnacademy.minidooray.gateway.adaptor.task.TaskProjectAdaptor;
import com.nhnacademy.minidooray.gateway.domain.task.Project;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectDto;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectMemberRequest;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskProjectServiceImpl implements TaskProjectService {

    private final TaskProjectAdaptor taskProjectAdaptor;

    public TaskProjectServiceImpl(TaskProjectAdaptor taskProjectAdaptor) {
        this.taskProjectAdaptor = taskProjectAdaptor;
    }


    @Override
    public List<ProjectDto> getListProjectDto(String id) {
        return taskProjectAdaptor.getProjectListByUserId(id);
    }

    @Override
    public List<ProjectMemberRequest> getListMemberOfProject(String projectId) {
        return taskProjectAdaptor.getProjectMemberList(projectId);
    }

    @Override
    public Project getSingleProject(String id) {
        return taskProjectAdaptor.getProjectById(id);
    }

    @Override
    public Project saveSingleProject(ProjectRequest projectRequest) {
        return taskProjectAdaptor.saveProject(projectRequest);
    }

    @Override
    public ProjectDto saveSingleProjectMember(int projectId, ProjectMemberRequest projectMemberRequest) {
        return taskProjectAdaptor.saveProjectMember(projectId, projectMemberRequest);
    }
}
