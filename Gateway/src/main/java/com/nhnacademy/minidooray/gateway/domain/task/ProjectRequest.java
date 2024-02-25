package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProjectRequest {

    private String projectName;
    private String projectDescription;
    private String adminId;

}
