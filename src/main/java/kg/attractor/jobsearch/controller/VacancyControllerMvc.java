package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller("vacancyController")
@RequestMapping("vacancies")
public class VacancyControllerMvc {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyControllerMvc(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAllVacancies(Model model) {
        List<VacancyDto> vacancyDtos = vacancyService.findAllVacancies();
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/vacancies";
    }
}
