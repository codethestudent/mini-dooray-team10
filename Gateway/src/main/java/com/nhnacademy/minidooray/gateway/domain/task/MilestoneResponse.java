package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MilestoneResponse {

    int milestoneId;
    String milestoneName;
    LocalDateTime startDate;
    LocalDateTime endDate;

}
