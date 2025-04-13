package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAll(Model model) {
        List<ResumeDto> resumeDtos = resumeService.findAllResumes();
        model.addAttribute("resumes", resumeDtos);
        return "resumes/resumes";
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUserResumes(Model model) {
        List<ResumeDto> resumeDtos = resumeService
                .findUserCreatedResumes();

        model.addAttribute("resumes", resumeDtos);
        return "resumes/resumes";
    }

    @GetMapping("{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public String findResumeById(@PathVariable Long resumeId, Model model) {
        model.addAttribute("resume", resumeService.findResumeById(resumeId));
        return "resumes/resume";
    }
}
