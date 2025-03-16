package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("vacancies")
public class VacancyController {

    @GetMapping("{vacancyId}")
    public ResponseEntity<VacancyDto> findVacancyById(@PathVariable Long vacancyId) {
        //ToDo implement handler for searching vacancy by Id

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping
    public HttpStatus createVacancy(@RequestBody VacancyDto vacancyDto) {
        //ToDO implement create vacancy handler

        return HttpStatus.CREATED;
    }

    @PutMapping("{vacancyId}")
    public HttpStatus redactorVacancy(@PathVariable Long vacancyId) {
        //ToDo implement redactor vacancy handler

        return HttpStatus.OK;
    }

    @DeleteMapping("{vacancyId}")
    public HttpStatus deleteVacancy(@PathVariable Long vacancyId) {
        //ToDO implement delete vacancy handler

        return HttpStatus.NO_CONTENT;
    }

    @GetMapping("actives")
    public ResponseEntity<List<VacancyDto>> findActiveVacancies() {
        //ToDO implement find all active vacancies handler

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("categories/{category}")
    public ResponseEntity<List<VacancyDto>> findVacanciesByCategory(@PathVariable String category) {
        //ToDo implement find all vacancies by category

        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @PostMapping("respond/{vacancyId}")
    public HttpStatus createRespond(@PathVariable Long vacancyId) {
        //ToDo implement create new respond handler

        return HttpStatus.CREATED;
    }
}
