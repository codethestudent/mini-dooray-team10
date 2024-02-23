package com.nhnacademy.minidooray.gateway.controller.task;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/task")
public class TaskController {

    @GetMapping("/dooray")
    public String goToDooray(){
        return "dooray";
    }

}
