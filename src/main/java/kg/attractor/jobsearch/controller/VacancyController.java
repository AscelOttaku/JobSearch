package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("vacancyController")
@RequestMapping("vacancies")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;

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

    @GetMapping("{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findVacancyById(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        return "vacancies/vacancy";
    }

    @GetMapping("new_vacancy")
    public String createVacancy(Model model) {
        model.addAttribute("vacancy", new VacancyDto());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/new_vacancy";
    }

    @PostMapping("new_vacancy")
    @ResponseStatus(HttpStatus.CREATED)
    public String createVacancy(@ModelAttribute("vacancy") @Valid VacancyDto vacancyDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("vacancy", vacancyDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "vacancies/new_vacancy";
        }

        VacancyDto vacancy = vacancyService.createdVacancy(vacancyDto);
        return "redirect:/vacancies/" + vacancy.getVacancyId();
    }

    @GetMapping("update/vacancy/{vacancyId}")
    public String updateVacancy(@PathVariable Long vacancyId, Model model) {
        VacancyDto vacancyDto = vacancyService.findAuthorizedUsersVacancyById(vacancyId);
        model.addAttribute("vacancy", vacancyDto);
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/new_vacancy";
    }

    @PutMapping("update/vacancy")
    public String updateVacancy(
            @ModelAttribute("vacancy") @Valid VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancy", vacancyDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "vacancies/new_vacancy";
        }

        VacancyDto vacancy = vacancyService.updateVacancy(vacancyDto);
        return "redirect:/vacancies/" + vacancy.getVacancyId();
    }
}
