package com.nhnacademy.minidooray.gateway.adaptor.task;

import com.nhnacademy.minidooray.gateway.config.TaskAdaptorProperties;
import com.nhnacademy.minidooray.gateway.domain.task.Project;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectDto;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectMemberRequest;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TaskProjectAdaptorImpl implements TaskProjectAdaptor{

    private final RestTemplate restTemplate;

    private final TaskAdaptorProperties taskAdaptorProperties;

    public TaskProjectAdaptorImpl(RestTemplate restTemplate, TaskAdaptorProperties taskAdaptorProperties) {
        this.restTemplate = restTemplate;
        this.taskAdaptorProperties = taskAdaptorProperties;
    }

    @Override
    public List<ProjectDto> getProjectListByUserId(String id) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(id, httpHeaders);
        ResponseEntity<List<ProjectDto>> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/member/" + id,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public List<ProjectMemberRequest> getProjectMemberList(String projectId) {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(projectId, httpHeaders);
        ResponseEntity<List<ProjectMemberRequest>> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/member/project/" + projectId,
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<List<ProjectMemberRequest>>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public Project getProjectById(String id) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(id, httpHeaders);
        ResponseEntity<Project> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project/" + id,
                HttpMethod.GET,
                requestHttpEntity,
                Project.class
        );

        return responseEntity.getBody();
    }

    @Override
    public Project saveProject(ProjectRequest projectRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<ProjectRequest> requestHttpEntity = new HttpEntity<>(projectRequest, httpHeaders);
        ResponseEntity<Project> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project",
                HttpMethod.POST,
                requestHttpEntity,
                Project.class
        );

        return responseEntity.getBody();
    }

    @Override
    public ProjectDto saveProjectMember(int projectId, ProjectMemberRequest projectMemberRequest) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        //HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(Map.of("userId", userId), httpHeaders);
        HttpEntity<ProjectMemberRequest> requestEntity = new HttpEntity<>(projectMemberRequest, httpHeaders);
        ResponseEntity<ProjectDto> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/member/project/"+projectId,
                HttpMethod.POST,
                requestEntity,
                ProjectDto.class
        );

        return responseEntity.getBody();
    }


}
