package com.nhnacademy.minidooray.taskapi.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int tagId;

    @Column(name = "tag_name")
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Tag() {
    }
}
