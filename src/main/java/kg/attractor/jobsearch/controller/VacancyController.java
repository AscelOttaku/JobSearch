package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller("vacancyController")
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAllVacancies(Model model) {
        List<VacancyDto> vacancyDtos = vacancyService.findAllVacancies();
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/vacancies";
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUserCreatedVacancies(Model model) {
        List<VacancyDto> vacancyDtos = vacancyService.findUserCreatedVacancies();
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/vacancies";
    }

    @GetMapping("users/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findVacancyById(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        return "vacancies/vacancy";
    }
}
