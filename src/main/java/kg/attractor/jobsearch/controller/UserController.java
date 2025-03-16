package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {

    @GetMapping("job-seeker/{userId}")
    public ResponseEntity<UserDto> findJobSeeker(@PathVariable Long userId, @RequestParam Long jobSearchId) {
        //ToDo implement search job seeker by id handler

        return ResponseEntity.ok(null);
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<List<UserDto>> findJobSeekersByVacancy(@PathVariable Long vacancyId, @RequestParam Long userId) {
        //ToDO implement search for job seeker that responded to the vacancy;

        return ResponseEntity.ok(null);
    }

    @GetMapping("employer/{userId}")
    public ResponseEntity<UserDto> findEmployer(@PathVariable Long userId, @RequestParam Long employerId) {
        //ToDo implement search employer by id handler

        return ResponseEntity.ok(null);
    }
}
