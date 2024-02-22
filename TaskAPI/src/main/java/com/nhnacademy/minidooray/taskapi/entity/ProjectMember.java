package com.nhnacademy.minidooray.taskapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "project_member")
@AllArgsConstructor
public class ProjectMember {
    @EmbeddedId
    private Pk pk;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    public ProjectMember() {
    }

    @Embeddable
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class Pk implements Serializable {
        @Column(name = "user_id")
        private String userId;

        @Column(name = "project_id")
        private int projectId;
    }
}
