package com.nhnacademy.minidooray.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.nhnacademy.minidooray.taskapi.domain.ProjectState;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "project")
public class Project {
    @Id
    @Column(name = "project_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int projectId;

    @Column(name = "project_name")
    private String projectName;

    @Column(name = "project_description")
    private String projectDescription;

    @Column(name = "admin_id")
    private String adminId;

    @Enumerated(EnumType.STRING)
    @Column(name = "project_state")
    private ProjectState projectState = ProjectState.ACTIVATED;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Task> tasks;
}
