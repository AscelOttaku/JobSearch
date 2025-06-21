package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.storage.TemporalStorage;
import kg.attractor.jobsearch.annotations.validators.ResumeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("resumes")
@SessionAttributes({"categories"})
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeDetailedInfoService resumeDetailedInfoService;
    private final CategoryService categoryService;
    private final ResumeValidator resumeValidator;
    private final VacancyService vacancyService;
    private final WorkExperienceInfoService workExperienceInfoService;
    private final EducationInfoService educationInfoService;
    private final TemporalStorage temporalStorage;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAll(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<ResumeDto> resumeDtos = resumeService.findAllResumes(page, size);
        model.addAttribute("pageResume", resumeDtos);
        model.addAttribute("categories", categoryService.findAllCategories());
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

    @GetMapping("users/actives/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findUserActiveResumes(
            Model model,
            @PathVariable Long vacancyId,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size
    ) {
        PageHolder<ResumeDto> pageResume = resumeService
                .findUserCreatedActiveResumes(page, size);

        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        model.addAttribute("pageResume", pageResume);
        model.addAttribute("hasResumes", !pageResume.getContent().isEmpty());
        return "vacancies/vacancy";
    }

    @GetMapping("{resumeId}")
    @ResponseStatus(HttpStatus.OK)
    public String findResumeById(@PathVariable Long resumeId, Model model) {
        model.addAttribute("resume", resumeService.findResumeById(resumeId));

        if (temporalStorage.isDataExist("respondedVacancies"))
            model.addAttribute("respondedVacancies", temporalStorage.getTemporalData("respondedVacancies", PageHolder.class));

        temporalStorage.removeTemporalData("respondedVacancies");
        return "resumes/resume";
    }

    @GetMapping("new_resume")
    @ResponseStatus(HttpStatus.CREATED)
    public String createResume(Model model) {
        ResumeDto resumeDtoModel = resumeDetailedInfoService.getResumeDtoModel();
        model.addAttribute("resume", resumeDtoModel);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "resumes/new_resume";
    }

    @PostMapping("new_resume")
    public String createResume(
            @ModelAttribute("resume") ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model,
            SessionStatus sessionStatus
    ) {
        resumeDto.setWorkExperienceInfoDtos(workExperienceInfoService.deleteEmptyWorkExperience(resumeDto.getWorkExperienceInfoDtos()));
        resumeDto.setEducationInfoDtos(educationInfoService.deleteEmptyEducation(resumeDto.getEducationInfoDtos()));

        resumeValidator.isValid(resumeDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("resume", resumeDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "resumes/new_resume";
        }

        Long resumeId = resumeDetailedInfoService.createResume(resumeDto);
        sessionStatus.setComplete();
        return "redirect:/resumes/" + resumeId;
    }

    @GetMapping("update/resume/{resumeId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateResumeById(@PathVariable Long resumeId, Model model) {
        ResumeDto resumeDto = resumeService.findPreparedResumeById(resumeId);

        model.addAttribute("resume", resumeDto);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "resumes/update_resume";
    }

    @PostMapping("update/resume")
    public String updateResume(
            @ModelAttribute("resume") ResumeDto resumeDto,
            BindingResult bindingResult,
            Model model,
            SessionStatus sessionStatus
    ) {
        resumeDto.setWorkExperienceInfoDtos(workExperienceInfoService.deleteEmptyWorkExperience(resumeDto.getWorkExperienceInfoDtos()));
        resumeDto.setEducationInfoDtos(educationInfoService.deleteEmptyEducation(resumeDto.getEducationInfoDtos()));

        resumeValidator.isValid(resumeDto, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("resume", resumeDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "resumes/update_resume";
        }

        resumeDetailedInfoService.updateResumeDetailedInfo(resumeDto);
        sessionStatus.setComplete();
        return "redirect:/resumes/" + resumeDto.getId();
    }

    @PostMapping("{resumeId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String deleteResumeById(@PathVariable Long resumeId) {
        resumeService.deleteResumeById(resumeId);
        return "redirect:/users/profile";
    }

    @GetMapping("responded/vacancy/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findUserRespondedResumes(
            @PathVariable Long vacancyId,
            Model model,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size
    ) {
        PageHolder<ResumeDto> pageResume = resumeService.findRespondedToVacancyResumes(vacancyId, page, size);

        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        model.addAttribute("respondedPageResumes", pageResume);
        return "vacancies/vacancy";
    }

    @GetMapping("sort")
    public String findResumeByCategory(
            @RequestParam String categoryName,
            @RequestParam(defaultValue = "0", required = false) Integer page,
            @RequestParam(defaultValue = "5", required = false) Integer size,
            Model model
    ) {
        model.addAttribute("pageResume", resumeService.findAllResumesByCategoryName(categoryName, page, size));
        model.addAttribute("categories", categoryService.findAllCategories());
        return "resumes/resumes";
    }
}

