package com.nhnacademy.minidooray.taskapi.controller;

import com.nhnacademy.minidooray.taskapi.domain.MilestoneDto;
import com.nhnacademy.minidooray.taskapi.entity.Milestone;
import com.nhnacademy.minidooray.taskapi.service.MilestoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/project/{project_id}/milestone")
@RequiredArgsConstructor
public class MilestoneController {
    private final MilestoneService milestoneService;

    @GetMapping
    public List<Milestone> getMilestones(@PathVariable int project_id) {
        return milestoneService.getMilestones(project_id);
    }

    @GetMapping("/{id}")
    public Milestone getMilestone(@PathVariable int project_id,
                                  @PathVariable int id) {
        return milestoneService.getMilestone(project_id, id);
    }

    @PostMapping
    public Milestone createMilestone(@PathVariable int project_id,
                                     @RequestBody Milestone milestone) {
        return milestoneService.createMilestone(project_id, milestone);
    }

    @PutMapping("/{id}")
    public Milestone updateMilestone(@PathVariable int project_id,
                                     @PathVariable int id,
                                     @RequestBody MilestoneDto milestoneDto) {
        return milestoneService.updateMilestone(project_id, id, milestoneDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMilestone(@PathVariable int project_id,
                                                  @PathVariable int id) {
        return milestoneService.deleteMilestone(project_id, id);
    }
}
