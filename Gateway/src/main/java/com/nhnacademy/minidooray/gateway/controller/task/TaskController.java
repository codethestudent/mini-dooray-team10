package com.nhnacademy.minidooray.gateway.controller.task;

import com.nhnacademy.minidooray.gateway.domain.account.AccountDto;
import com.nhnacademy.minidooray.gateway.domain.task.*;
import com.nhnacademy.minidooray.gateway.service.account.AccountService;
import com.nhnacademy.minidooray.gateway.service.task.TaskProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
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
    public String goToDooray(Model model, HttpSession session){

        List<ProjectDto> projectDtoList = taskProjectService.getListProjectDto((String) session.getAttribute("userId"));

        if(!projectDtoList.isEmpty()) {
            model.addAttribute("projectList", projectDtoList);
        }

        return "dooray";
    }

    @GetMapping("/project/{id}")
    public String getProject(@PathVariable("id") String projectId, Model model) {

        log.info("프로젝트 단건조회 프로젝트 ID : {}", projectId);

        Project project = taskProjectService.getSingleProject(projectId);
        //프로젝트 리스트 추가
        model.addAttribute("project", project);

        //member 리스트 추가
        List<ProjectMemberRequest> memberRequestList = taskProjectService.getListMemberOfProject(projectId);
        model.addAttribute("memberList", memberRequestList);
        //task 리스트 추가
        // todo
        //tag 리스트 추가
        List<TagResponse> tagResponseList = taskProjectService.getListTag(projectId);

        for(TagResponse tag : tagResponseList ) {
            log.info("tag name : {}", tag.getTagName());
        }

        model.addAttribute("tagList", tagResponseList);
        //마일스톤 추가
        // todo

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
            // todo else if (p != null) 으로 변경 해보기
            //assert p != null;
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

    @GetMapping("/tag/{id}")
    public String goToTagForm(@PathVariable("id") String projectId, Model model) {
        log.info("태그 추가 요청 프로젝트 아이디 : {}", projectId);
        model.addAttribute("projectId", projectId);

        return "tag-form";
    }

    @PostMapping("/tag/form")
    public String saveTag(@ModelAttribute TagRequest tagRequest, @RequestParam("projectId") String projectId, RedirectAttributes redirectAttributes) {

        TagResponse tagResponse = taskProjectService.saveSingleProjectTag(tagRequest, projectId);

        List<TagResponse> tagResponseList = taskProjectService.getListTag(projectId);

        redirectAttributes.addFlashAttribute("tagList", tagResponseList);

        return "redirect:/task/project/"+projectId;
    }

    @GetMapping("/milestone/{id}")
    public String goToMilestoneForm(@PathVariable("id") String projectId, Model model) {
        log.info("마일스톤 추가 요청 프로젝트 아이디 : {}", projectId);

        model.addAttribute("projectId", projectId);

        return "milestone-form";
    }

    @PostMapping("/milestone/create")
    public String saveMilestone(@ModelAttribute MilestoneRequest milestoneRequest, @RequestParam("startDate") LocalDateTime start, @RequestParam("endDate") LocalDateTime end, @RequestParam("projectId") String projectId, RedirectAttributes redirectAttributes) {
        log.info("startDate : {}", start);
        log.info("endDate : {}", end);
        log.info("Milestone : {}", milestoneRequest);
        // 저장
        MilestoneResponse milestoneResponse = taskProjectService.saveSingleMilestone(milestoneRequest, projectId);

        // 마일스톤 리스트
        List<MilestoneResponse> milestoneResponseList = taskProjectService.getListMilestoneByProjectId(projectId);
        redirectAttributes.addFlashAttribute("milestoneList", milestoneResponseList);

        return "redirect:/project/"+projectId;
    }


//    @GetMapping("/project/task/form/{id}")
//    public String tryToSaveTask(@PathVariable("id") String projectId) {
//
//        // todo 업무 등록전에 마일스톤 리스트 들고 오기
//
//        return "";
//    }
}
