package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.ProjectDto;
import com.nhnacademy.minidooray.taskapi.domain.ProjectMemberDto;
import com.nhnacademy.minidooray.taskapi.service.ProjectMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/member")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @GetMapping("/project/{projectId}")
    public List<ProjectMemberDto> getProjectMembers(@PathVariable int projectId) {
        return projectMemberService.getProjectMembers(projectId);
    }

    @GetMapping("/{id}")
    public List<ProjectDto> getProjects(@PathVariable String id) {
        return projectMemberService.getProjects(id);
    }

    @PostMapping("/project/{projectId}")
    public ProjectMemberDto createProjectMember(@PathVariable int projectId,
                                                @RequestBody ProjectMemberDto projectMemberDto) {
        return projectMemberService.createProjectMember(projectId, projectMemberDto);
    }

    @DeleteMapping("{id}/project/{projectId}")
    public ResponseEntity<String> deleteProjectMember(@PathVariable int projectId,
                                                      @PathVariable String id) {
        return projectMemberService.deleteProjectMember(projectId, id);
    }
}
