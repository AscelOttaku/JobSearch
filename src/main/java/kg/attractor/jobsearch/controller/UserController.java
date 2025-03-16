package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("job-seekers")
    public HttpStatus createJobSeeker(@RequestBody UserDto userDto) {
        //ToDo implement creating accaunt job-seeker

        return HttpStatus.CREATED;
    }

    @PostMapping("employers")
    public HttpStatus createEmployer(@RequestBody UserDto userDto) {
        //ToDo implement creating employer account

        return HttpStatus.CREATED;
    }

    @GetMapping("job-seeker/{userId}")
    public ResponseEntity<UserDto> findJobSeeker(@PathVariable Long userId) {
        //ToDo implement search job seeker by id handler

        return ResponseEntity.ok(null);
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<List<UserDto>> findJobSeekersByVacancy(@PathVariable Long vacancyId) {
        //ToDO implement search for job seeker that responded to the vacancy;

        return ResponseEntity.ok(null);
    }

    @GetMapping("employer/{userId}")
    public ResponseEntity<UserDto> findEmployer(@PathVariable Long userId) {
        //ToDo implement search employer by id handler

        return ResponseEntity.ok(null);
    }

    @PostMapping("upload/avatars")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) {
        //ToDO logic for storing avatar and user

        return userService.uploadAvatar(file);
    }
}
