package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.ResumeDetailedInfoService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.validators.ResumeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeDetailedInfoService resumeDetailedInfoService;
    private final CategoryService categoryService;
    private final ResumeValidator resumeValidator;
    private final VacancyService vacancyService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<ResumeDto> resumeDtos = resumeService.findAllResumes(page, size);
        model.addAttribute("pageResume", resumeDtos);
        return "resumes/resumes";
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUserResumes(
            Model model,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<ResumeDto> pageResume = resumeService
                .findUserCreatedResumes(page, size);

        model.addAttribute("pageResume", pageResume);
        return "resumes/user_resumes";
    }

    @GetMapping("users/actives")
    @ResponseStatus(HttpStatus.OK)
    public String findUserActiveResumes(
            Model model,
            @RequestParam Long vacancyId,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size
    ) {
        PageHolder<ResumeDto> pageResume = resumeService
                .findUserCreatedActiveResumes(page, size);

        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        model.addAttribute("pageResume", pageResume);
        return "vacancies/vacancy";
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
            @ModelAttribute("resume") ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model
    ) {
        resumeValidator.isValid(resumeDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("resume", resumeDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "resumes/new_resume";
        }

        Long resumeId = resumeDetailedInfoService.createResume(resumeDto);
        return "redirect:/resumes/" + resumeId;
    }

    @GetMapping("update/resume/{resumeId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateResumeById(@PathVariable Long resumeId, Model model) {
        ResumeDto resumeDto = resumeService.findResumeById(resumeId);

        model.addAttribute("resume", resumeDto);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "resumes/update_resume";
    }

    @PostMapping("update/resume")
    public String updateResume(
            @ModelAttribute("resume") ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model) {

        resumeValidator.isValid(resumeDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("resume", resumeDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "resumes/update_resume";
        }

        resumeDetailedInfoService.updateResumeDetailedInfo(resumeDto);
        return "redirect:/resumes/" + resumeDto.getId();
    }

    @DeleteMapping("{resumeId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String deleteResumeById(@PathVariable Long resumeId) {
        resumeService.deleteResumeById(resumeId);
        return "redirect:/users/profile";
    }
}

