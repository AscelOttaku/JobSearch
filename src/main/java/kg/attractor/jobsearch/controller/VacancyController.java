package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
public class VacancyController {
    private final VacancyService vacancyService;

    @Autowired
    public VacancyController(VacancyService vacancyService) {
        this.vacancyService = vacancyService;
    }

    @GetMapping("{vacancyId}")
    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable Long vacancyId) {
        //ToDo implement handler for searching vacancy by Id

        return vacancyService.findVacancyById(vacancyId)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        //ToDO implement create vacancy handler

        return vacancyService.createVacancy(vacancyDto) != 1 ?
                HttpStatus.CREATED :
                HttpStatus.BAD_REQUEST;
    }

    @PutMapping()
    public HttpStatus redactorVacancy(@RequestBody VacancyDto vacancyDto) {
        //ToDo implement redactor vacancy handler

        return vacancyService.updateVacancy(vacancyDto) != 1 ?
                HttpStatus.NO_CONTENT :
                HttpStatus.BAD_REQUEST;
    }

    @DeleteMapping("{vacancyId}")
    public HttpStatus deleteVacancy(@PathVariable Long vacancyId) {
        //ToDO implement delete vacancy handler

        return vacancyService.deleteVacancy(vacancyId) ?
                HttpStatus.NO_CONTENT :
                HttpStatus.NOT_FOUND;
    }

    @GetMapping("actives")
    public ResponseEntity<List<VacancyDto>> findActiveVacancies() {
        //ToDO implement find all active vacancies handler

        return vacancyService.findActiveVacancies().isEmpty() ?
                new ResponseEntity<>(HttpStatus.NO_CONTENT) :
                new ResponseEntity<>(vacancyService.findActiveVacancies(), HttpStatus.OK);
    }

    @GetMapping("categories/{category}")
    public ResponseEntity<List<VacancyDto>> findVacanciesByCategory(@PathVariable String category) {
        //ToDo implement find all vacancies by category

        return vacancyService.findVacanciesByCategory(category)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @PostMapping("respond/{vacancyId}/{resumeId}")
    public HttpStatus createRespond(@PathVariable Long vacancyId, @PathVariable Long resumeId) {
        //ToDo implement create new respond handler

        return vacancyService.createRespond(vacancyId, resumeId) != 1 ?
                HttpStatus.NO_CONTENT :
                HttpStatus.BAD_REQUEST;
    }
}
