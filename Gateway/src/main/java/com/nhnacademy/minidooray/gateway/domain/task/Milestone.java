package com.nhnacademy.minidooray.gateway.domain.task;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Milestone {

    int milestoneId;
    String milestoneName;
    LocalDateTime start_date;
    LocalDateTime end_date;

}
