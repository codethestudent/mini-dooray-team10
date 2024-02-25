package com.nhnacademy.minidooray.taskapi.domain;

import lombok.Data;

import javax.persistence.Column;

@Data
public class TagDto {
    private int tagId;

    private String tagName;

    public TagDto(int tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
