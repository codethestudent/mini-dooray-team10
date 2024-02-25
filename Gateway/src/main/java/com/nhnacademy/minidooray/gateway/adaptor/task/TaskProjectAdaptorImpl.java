package com.nhnacademy.minidooray.gateway.adaptor.task;

import com.nhnacademy.minidooray.gateway.config.TaskAdaptorProperties;
import com.nhnacademy.minidooray.gateway.domain.task.*;
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

    @Override
    public List<TagResponse> getProjectTagList(String projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<String> requestHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<TagResponse>> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project/" + projectId + "/tag",
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<List<TagResponse>>() {
                }
        );

        return responseEntity.getBody();
    }

    @Override
    public TagResponse saveProjectTag(TagRequest tagRequest, String projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        log.info("태그 등록 객체 : {}", tagRequest);
        log.info("태그 등록 프로젝트 아이디 : {}", projectId);
        HttpEntity<TagRequest> requestHttpEntity = new HttpEntity<>(tagRequest, httpHeaders);
        ResponseEntity<TagResponse> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project/"+projectId+"/tag",
                HttpMethod.POST,
                requestHttpEntity,
                TagResponse.class
        );

        return responseEntity.getBody();
    }

    @Override
    public MilestoneResponse saveProjectMilestone(MilestoneRequest milestoneRequest, String projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        log.info("save milestone : {}", milestoneRequest);
        HttpEntity<MilestoneRequest> requestHttpEntity = new HttpEntity<>(milestoneRequest, httpHeaders);
        ResponseEntity<MilestoneResponse> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project/"+projectId+"/milestone",
                HttpMethod.POST,
                requestHttpEntity,
                MilestoneResponse.class
        );
        return responseEntity.getBody();
    }

    @Override
    public List<MilestoneResponse> getMilestoneListByProjectId(String projectId) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));

        log.info("get milestone List ProjectId : {}", projectId);
        HttpEntity<Void> requestHttpEntity = new HttpEntity<>(httpHeaders);
        ResponseEntity<List<MilestoneResponse>> responseEntity = restTemplate.exchange(
                taskAdaptorProperties.getUrlName() + "/api/project/" + projectId + "/milestone",
                HttpMethod.GET,
                requestHttpEntity,
                new ParameterizedTypeReference<List<MilestoneResponse>>() {
                }
        );

        return responseEntity.getBody();
    }


}
