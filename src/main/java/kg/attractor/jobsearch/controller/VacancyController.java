package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public VacancyDto findVacancyById(@PathVariable Long vacancyId) {
        return vacancyService.findVacancyById(vacancyId);
    }

    @PostMapping("new-vacancies")
    @ResponseStatus(HttpStatus.CREATED)
    public VacancyDto createVacancy(
            @RequestBody @Valid VacancyDto vacancyDto
    ) {
        return vacancyService.createdVacancy(vacancyDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public VacancyDto redactorVacancy(
            @RequestParam Long vacancyId,
            @RequestBody @Valid VacancyDto vacancyDto
    ) {
        return vacancyService.updateVacancy(vacancyId, vacancyDto);
    }

    @DeleteMapping("{vacancyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVacancy(
            @PathVariable Long vacancyId
    ) {
        vacancyService.deleteVacancy(vacancyId);
    }

    @GetMapping("actives")
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyDto> findAllActiveVacancies() {
        return vacancyService.findAllActiveVacancies();
    }

    @GetMapping("categories")
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyDto> findVacanciesByCategory(@RequestBody Long categoryId) {
        return vacancyService.findVacanciesByCategory(categoryId);
    }

    @GetMapping("users/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyDto> findUserRespondedVacancies(
            @PathVariable @Email(message = "{email_message}")
            String userEmail
    ) {
        return vacancyService.findUserRespondedVacancies(userEmail);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VacancyDto> findAllVacancies() {
        return vacancyService.findAllVacancies();
    }
}
