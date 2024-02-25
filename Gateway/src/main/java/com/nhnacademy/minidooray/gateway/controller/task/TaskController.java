package com.nhnacademy.minidooray.gateway.controller.task;

import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.task.Project;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectDto;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectMemberRequest;
import com.nhnacademy.minidooray.gateway.domain.task.ProjectRequest;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import com.nhnacademy.minidooray.gateway.service.task.TaskProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/task")
public class TaskController {

    private final TaskProjectService taskProjectService;
    private final AccountService accountService;

    public TaskController(TaskProjectService taskProjectService, AccountService accountService) {
        this.taskProjectService = taskProjectService;
        this.accountService = accountService;
    }

    @GetMapping("/dooray")
    public String goToDooray(Model model){

        log.info("로그인 성공 후 두라이 홈 id : {}", model.getAttribute("id"));

        List<ProjectDto> projectDtoList = taskProjectService.getListProjectDto((String) model.getAttribute("id"));

        if(!projectDtoList.isEmpty()) {
            model.addAttribute("projectList", projectDtoList);
        }

        return "dooray";
    }

    @GetMapping("/project/{id}")
    public String getProject(@PathVariable("id") String id, Model model) {

        log.info("프로젝트 단건조회 프로젝트 ID : {}", id);

        Project project = taskProjectService.getSingleProject(id);

        List<ProjectMemberRequest> memberRequestList = taskProjectService.getListMemberOfProject(id);
        //task 리스트 추가
        //tag 리스트 추가
        //마일스톤 추가

        model.addAttribute("project", project);
        model.addAttribute("memberList", memberRequestList);
        return "project-detail";
    }

    @GetMapping("/projectForm")
    public String getProjectForm() {
        return "project-form";
    }

    /**
     * /project/member/form - 프로젝트 추가 후 보내는 요청
     * /project/member/form/{id} - 단건 프로젝트 선택 후 멤버 추가하는 요청
     * @param model
     * @param projectId - 단건 프로젝트 선택 후 추가하는 요청으로 부터 받아오는 project_id
     * @param request
     * @return
     */
    @GetMapping({"/project/member/form","/project/member/form/{id}"})
    public String goToProjectMemberForm(Model model, @PathVariable(value = "id", required = false) String projectId, HttpServletRequest request) {
        log.info("멤버 추가 URL : {}", request.getRequestURL());

        Project p = (Project) model.getAttribute("project");

        if(Objects.nonNull(projectId)) {
            List<AccountDto> accountList = accountService.getAccountList();
            model.addAttribute("projectId", projectId);
            model.addAttribute("accountList", accountList);
        } else {
            model.addAttribute("projectId", p.getProjectId());
            log.info("프로젝트 멤버 추가 redirect 요청 project : {}", p.getProjectId());
        }

        log.info("프로젝트 멤버 추가 redirect 요청 AccountDtoList size : {}", model.getAttribute("accountList"));

        return "project-member-form";
    }

    @PostMapping("/project/submit")
    public String tryToSaveProjectForm(@ModelAttribute ProjectRequest projectRequest, RedirectAttributes redirectAttributes) {

        log.info("project save projectRequest - {}",projectRequest);

        Project project = taskProjectService.saveSingleProject(projectRequest);

        List<AccountDto> accountList = accountService.getAccountList();

        redirectAttributes.addFlashAttribute("project", project);
        redirectAttributes.addFlashAttribute("accountList", accountList);

        return "redirect:/task/project/member/form";
    }

    @PostMapping("/project/member")
    public String tryToSaveProjectMember(@ModelAttribute ProjectMemberRequest projectMemberRequest, HttpSession session, RedirectAttributes redirectAttributes) {

        log.info("project member 추가 projectId : {}", projectMemberRequest.getProjectId());
        log.info("project member 추가 userId : {}", projectMemberRequest.getUserId());

        ProjectDto projectDto = taskProjectService.saveSingleProjectMember(projectMemberRequest.getProjectId(), projectMemberRequest);

        redirectAttributes.addFlashAttribute("id", session.getAttribute("userId"));

        return "redirect:/task/dooray";
    }
}
