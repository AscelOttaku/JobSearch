package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public HttpStatus createUser(@RequestBody UserDto userDto) {
        //ToDo implement creating accaunt job-seeker

        return userService.createUser(userDto) != 1 ?
                HttpStatus.CREATED :
                HttpStatus.BAD_REQUEST;
    }

    @GetMapping("job-seeker/{userId}")
    public ResponseEntity<UserDto> findJobSeeker(@PathVariable Long userId) {
        //ToDo implement search job seeker by id handler

        Optional<UserDto> userDto = userService.findJobSeeker(userId);

        return userDto.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("vacancies/{vacancyId}")
    public ResponseEntity<UserDto> findJobSeekersByVacancy(@PathVariable Long vacancyId) {
        //ToDO implement search for job seeker that responded to the vacancy;

        return userService.findJobSeekerByVacancyId(vacancyId)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("employer/{userId}")
    public ResponseEntity<UserDto> findEmployerById(@PathVariable Long userId) {
        //ToDo implement search employer by id handler

        return userService.findEmployerById(userId)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("upload/avatars")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) throws IOException {
        //ToDO logic for storing avatar and user

        return new ResponseEntity<>(userService.uploadAvatar(file), HttpStatus.OK);
    }
}
