package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeDetailedInfoService resumeDetailedInfoService;
    private final CategoryService categoryService;

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

    @GetMapping("new_resume")
    @ResponseStatus(HttpStatus.CREATED)
    public String createResume(Model model) {
        model.addAllAttributes(resumeDetailedInfoService.getResumeDtoModel());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "resumes/new_resume";
    }

    @PostMapping("new_resume")
    public String createResume(
            @ModelAttribute("resume") @Valid ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("resume", resumeDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "resumes/new_resume";
        }

        Long resumeId = resumeDetailedInfoService.createResume(resumeDto);
        return "redirect:/resumes/" + resumeId;
    }
}
