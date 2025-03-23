package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleInCaseUserNotFoundAndIllegalArgException;

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
        return userService.createUser(userDto) != -1 ?
                HttpStatus.CREATED :
                HttpStatus.BAD_REQUEST;
    }

    @GetMapping("job-seeker/{userEmail}")
    public ResponseEntity<UserDto> findJobSeekerByEmail(@PathVariable String userEmail) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findJobSeekerByEmail(userEmail));
    }

    @GetMapping("employer/{employerEmail}")
    public ResponseEntity<UserDto> findEmployerByEmail(@PathVariable String employerEmail) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findEmployerByEmail(employerEmail));
    }

    @PostMapping("upload/avatars")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) throws IOException {
        //ToDO logic for storing avatar and user

        return new ResponseEntity<>(userService.uploadAvatar(file), HttpStatus.OK);
    }

    @GetMapping("names/{userName}")
    public ResponseEntity<Set<UserDto>> findUsersByName(@PathVariable String userName) {
        return new ResponseEntity<>(userService.findUsersByName(userName), HttpStatus.OK);
    }

    @GetMapping("emails/{userEmail}")
    public ResponseEntity<UserDto> findUserByEmail(@PathVariable String userEmail) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findUserByEmail(userEmail));
    }

    @GetMapping("phones/{userPhoneNumber}")
    public ResponseEntity<UserDto> findUserByPhoneNumber(@PathVariable String userPhoneNumber) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findUserByPhoneNumber(userPhoneNumber));
    }

    @GetMapping("exist/{userEmail}")
    public HttpStatus isUserExist(@PathVariable String userEmail) {
        return userService.isUserExistByEmail(userEmail) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @GetMapping("responded/vacancies/{vacancyId}")
    public List<UserDto> findRespondedToVacancyUsersByVacancy(@PathVariable Long vacancyId) {
        return userService.findRespondedToVacancyUsersByVacancy(vacancyId);
    }
}
