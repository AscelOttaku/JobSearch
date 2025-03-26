package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import static kg.attractor.jobsearch.util.ExceptionHandler.*;

@RestController
@Validated
@RequestMapping("users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody @Valid UserDto userDto) {
        Long userId = userService.createUser(userDto);

        return userId != -1 ?
                new ResponseEntity<>(userId, HttpStatus.CREATED) :
                ResponseEntity.badRequest().build();
    }

    @GetMapping("job-seeker/{userEmail}")
    public ResponseEntity<UserDto> findJobSeekerByEmail(
            @PathVariable @NotBlank @Email(message = "{email_message}") String userEmail
    ) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findJobSeekerByEmail(userEmail));
    }

    @GetMapping("employer/{employerEmail}")
    public ResponseEntity<UserDto> findEmployerByEmail(
            @PathVariable @NotBlank @Email String employerEmail
    ) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findEmployerByEmail(employerEmail));
    }

    @PostMapping("upload/avatars")
    public ResponseEntity<String> uploadAvatar(MultipartFile file) throws IOException {
        //ToDO logic for storing avatar and user

        return new ResponseEntity<>(userService.uploadAvatar(file), HttpStatus.OK);
    }

    @GetMapping("names/{userName}")
    public ResponseEntity<Set<UserDto>> findUsersByName(
            @PathVariable @NotBlank String userName
    ) {
        return new ResponseEntity<>(userService.findUsersByName(userName), HttpStatus.OK);
    }

    @GetMapping("emails/{userEmail}")
    public ResponseEntity<UserDto> findUserByEmail(
            @PathVariable @NotBlank @Email String userEmail
    ) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findUserByEmail(userEmail));
    }

    @GetMapping("phones/{userPhoneNumber}")
    public ResponseEntity<UserDto> findUserByPhoneNumber(
            @PathVariable @NotBlank @Size(min = 12, max = 12)
            @Pattern(regexp = "^\\+?[0-9\\-\\s]+$") String userPhoneNumber
    ) {
        return handleInCaseUserNotFoundAndIllegalArgException(() -> userService.findUserByPhoneNumber(userPhoneNumber));
    }

    @GetMapping("exist/{userEmail}")
    public HttpStatus isUserExist(
            @PathVariable @NotBlank @Email String userEmail
    ) {
        return userService.isUserExistByEmail(userEmail) ? HttpStatus.OK : HttpStatus.NOT_FOUND;
    }

    @GetMapping("responded/vacancies/{vacancyId}")
    public List<UserDto> findRespondedToVacancyUsersByVacancy(
            @PathVariable @Positive Long vacancyId
    ) {
        return userService.findRespondedToVacancyUsersByVacancy(vacancyId);
    }

    @PutMapping
    public ResponseEntity<Void> updateUser(
            @RequestParam @Positive Long userId, @RequestBody @Valid UserDto userDto
    ) {
        return handleException(() -> userService.updateUser(userId, userDto));
    }
}
