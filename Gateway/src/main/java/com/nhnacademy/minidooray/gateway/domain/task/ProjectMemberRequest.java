package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectMemberRequest {

    private String userId;
    private int projectId;

}
