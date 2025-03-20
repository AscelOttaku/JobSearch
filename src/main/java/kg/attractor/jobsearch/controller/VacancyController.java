package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleVacancyNotFoundException;

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
        return handleVacancyNotFoundException(() -> vacancyService.findVacancyById(vacancyId));
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
        return vacancyService.deleteVacancy(vacancyId) ?
                HttpStatus.NO_CONTENT :
                HttpStatus.NOT_FOUND;
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

    @PostMapping("respond/{vacancyId}/{resumeId}")
    public HttpStatus createRespond(@PathVariable Long vacancyId, @PathVariable Long resumeId) {
        //ToDo implement create new respond handler

        return vacancyService.createRespond(vacancyId, resumeId) != 1 ?
                HttpStatus.NO_CONTENT :
                HttpStatus.BAD_REQUEST;
    }

    @GetMapping("users")
    public ResponseEntity<List<VacancyDto>> findUserRespondedVacancies(@RequestBody User user) {
        try {
            return new ResponseEntity<>(vacancyService.findUserRespondedVacancies(user), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<VacancyDto>> findAllVacancies() {
        return new ResponseEntity<>(vacancyService.findAllVacancies(), HttpStatus.OK);
    }
}
