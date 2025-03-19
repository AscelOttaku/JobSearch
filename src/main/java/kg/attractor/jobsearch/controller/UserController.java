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

    @GetMapping("job-seeker/{userEmail}")
    public ResponseEntity<UserDto> findJobSeekerByEmail(@PathVariable String userEmail) {
        //ToDo implement search job seeker by id handler

        Optional<UserDto> userDto = userService.findJobSeekerByEmail(userEmail);

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

    @GetMapping("employer/{employerEmail}")
    public ResponseEntity<UserDto> findEmployerByEmail(@PathVariable String employerEmail) {
        //ToDo implement search employer by email handler

        return userService.findEmployerByEmail(employerEmail)
                .map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("upload/avatars")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) throws IOException {
        //ToDO logic for storing avatar and user

        return new ResponseEntity<>(userService.uploadAvatar(file), HttpStatus.OK);
    }
}
