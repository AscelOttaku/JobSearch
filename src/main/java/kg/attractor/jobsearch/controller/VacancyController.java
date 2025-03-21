package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleIllegalArgumentException;
import static kg.attractor.jobsearch.util.ExceptionHandler.handleVacancyNotFoundAndIllegalArgException;

@RestController
@RequestMapping("/vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("{vacancyId}")
    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable Long vacancyId) {
        return handleVacancyNotFoundAndIllegalArgException(() -> vacancyService.findVacancyById(vacancyId));
    }

    @PostMapping("new-vacancies")
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody VacancyDto vacancyDto) {
        return handleVacancyNotFoundAndIllegalArgException(() -> vacancyService.createdVacancy(vacancyDto));
    }

    @PutMapping
    public ResponseEntity<VacancyDto> redactorVacancy(@RequestBody VacancyDto vacancyDto) {
        return handleVacancyNotFoundAndIllegalArgException(() -> vacancyService.updateVacancy(vacancyDto));
    }

    @DeleteMapping("{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long vacancyId) {
        return vacancyService.deleteVacancy(vacancyId) ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("actives")
    public ResponseEntity<List<VacancyDto>> findAllActiveVacancies() {
        return vacancyService.findAllActiveVacancies().isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(vacancyService.findAllActiveVacancies(), HttpStatus.OK);
    }

    @GetMapping("categories")
    public ResponseEntity<List<VacancyDto>> findVacanciesByCategory(@RequestBody Category category) {
        return new ResponseEntity<>(vacancyService.findVacanciesByCategory(category), HttpStatus.OK);
    }

    @GetMapping("users")
    public ResponseEntity<List<VacancyDto>> findUserRespondedVacancies(@RequestBody UserDto userDto) {
        return handleIllegalArgumentException(() -> vacancyService.findUserRespondedVacancies(userDto));
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> findAllVacancies() {
        return new ResponseEntity<>(vacancyService.findAllVacancies(), HttpStatus.OK);
    }
}
