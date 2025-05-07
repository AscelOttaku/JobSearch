package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacanciesFilterService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller("vacancyController")
@RequestMapping("/vacancies")
@Slf4j
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;
    private final CategoryService categoryService;
    private final VacanciesFilterService vacanciesFilterService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public String findAllVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<VacancyDto> vacancyDtos = vacancyService.findAllVacancies(page, size);
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/vacancies";
    }

    @GetMapping("actives")
    @ResponseStatus(HttpStatus.OK)
    public String findAllActiveVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "20", required = false) Integer size
    ) {
        model.addAttribute("vacancies", vacancyService.findAllActiveVacancies(page, size));
        return "vacancies/vacancies";
    }

    @GetMapping("users")
    @ResponseStatus(HttpStatus.OK)
    public String findUserCreatedVacancies(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        PageHolder<VacancyDto> vacancyDtos = vacancyService.findUserCreatedVacancies(page, size);
        model.addAttribute("vacancies", vacancyDtos);
        return "vacancies/user_vacancies";
    }

    @GetMapping("{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public String findVacancyById(@PathVariable Long vacancyId, Model model) {
        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        return "vacancies/vacancy";
    }

    @GetMapping("/new_vacancy")
    public String createVacancy(Model model) {
        model.addAttribute("vacancy", new VacancyDto());
        model.addAttribute("categories", categoryService.findAllCategories());
        return "vacancies/new_vacancy";
    }

    @PostMapping("/new_vacancy")
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
        return "vacancies/update_vacancy";
    }

    @PostMapping("update/vacancy")
    public String updateVacancy(
            @ModelAttribute("vacancy") @Valid VacancyDto vacancyDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("vacancy", vacancyDto);
            model.addAttribute("categories", categoryService.findAllCategories());
            return "vacancies/update_vacancy";
        }

        VacancyDto vacancy = vacancyService.updateVacancy(vacancyDto);
        return "redirect:/vacancies/" + vacancy.getVacancyId();
    }

    @PostMapping("times")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateVacancyTime(@RequestParam("vacancyId") Long vacancyId) {
        log.info("text = {}", vacancyId);
        vacancyService.updateVacancyDate(vacancyId);

        return "redirect:/users/profile";
    }

    @GetMapping("filtered")
    public String filterVacanciesBy(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) FilterType filterType,
            Model model
    ) {
        if (filterType == null) return "redirect:/vacancies/actives?page=" + page + "&size=" + size;

        model.addAttribute("vacancies", vacanciesFilterService.filterVacanciesBy(filterType, page, size));
        return "vacancies/vacancies";
    }

    @GetMapping("users/filtered")
    public String filterUsersVacancies(
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
            @RequestParam(required = false) FilterType filterType,
            Model model
    ) {
        if (filterType == null) return "redirect:/vacancies/users?page=" + page + "&size=" + size;

        model.addAttribute("vacancies", vacanciesFilterService.filterUserCreatedVacanciesBy(filterType, page, size));
        return "vacancies/user_vacancies";
    }
}
