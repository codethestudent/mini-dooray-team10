package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TagRequest {

    private int projectId;
    private String tagName;

}
