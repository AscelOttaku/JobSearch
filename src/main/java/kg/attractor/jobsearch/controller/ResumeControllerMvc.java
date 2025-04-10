package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
@RequestMapping("resumes")
public class ResumeControllerMvc {
    private final ResumeService resumeService;

    @Autowired
    public ResumeControllerMvc(ResumeService resumeService) {
        this.resumeService = resumeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAll(Model model) {
        List<ResumeDto> resumeDtos = resumeService.findAllResumes();
        model.addAttribute("resumes", resumeDtos);
        return "resumes/resumes";
    }

    @GetMapping("users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String findUserResumes(@PathVariable Long userId, Model model) {
        List<ResumeDto> resumeDtos = resumeService.findResumeByUserId(userId);
        model.addAttribute("resumes", resumeDtos);
        return "resumes/resumes";
    }
}
