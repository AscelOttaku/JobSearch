package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

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

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUsersResponds(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        PageHolder<RespondApplicationDto> responds = respondService.findUserResponds(page, size);

        model.addAttribute("responds", responds.getContent().stream()
                .collect(Collectors.toMap(
                                respond -> vacancyService.findVacancyById(respond.getVacancyId()),
                                respond -> resumeService.findResumeById(respond.getResumeId())
                        )
                )
        );
        responds.setContent(null);
        model.addAttribute("pageHolder", responds);

        return "responds/user_responds";
    }

    @GetMapping("employers")
    @ResponseStatus(HttpStatus.OK)
    public String findEmployerResponds(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "10") Integer size,
            Model model
    ) {
        model.addAttribute("responds", respondService.findEmployerResponds(page, size));

        return "responds/employer_responds";
    }
}
