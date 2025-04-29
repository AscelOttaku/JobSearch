package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/responds")
public class RespondApplicationController {
    private final RespondService respondService;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;

    public RespondApplicationController(RespondService respondService, ResumeService resumeService, VacancyService vacancyService) {
        this.respondService = respondService;
        this.resumeService = resumeService;
        this.vacancyService = vacancyService;
    }

    @PostMapping
    public String makeRespond(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "5", required = false) Integer size,
            @Valid RespondApplicationDto respondApplicationDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancy", vacancyService.findVacancyById(respondApplicationDto.getVacancyId()));
            model.addAttribute("pageResume", resumeService.findUserCreatedActiveResumes(
                    page, size
            ));
            model.addAttribute("error", bindingResult.getFieldError("resumeId"));
            model.addAttribute("selectedResumeId", respondApplicationDto.getResumeId());
            return "vacancies/vacancy";
        }

        respondService.createRespond(respondApplicationDto);
        return "redirect:/vacancies/" + respondApplicationDto.getVacancyId();
    }
}
