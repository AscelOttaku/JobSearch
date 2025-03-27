package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.marks.CreateOn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

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
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(
            @RequestBody @Validated(CreateOn.class) UserDto userDto
    ) {
        return userService.createUser(userDto);
    }

    @GetMapping("job-seeker/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findJobSeekerByEmail(
            @PathVariable @NotBlank @Email(message = "{email_message}") String userEmail
    ) {
        return userService.findJobSeekerByEmail(userEmail);
    }

    @GetMapping("employer/{employerEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findEmployerByEmail(
            @PathVariable @NotBlank @Email String employerEmail
    ) {
        return userService.findEmployerByEmail(employerEmail);
    }

    @PostMapping("upload/avatars")
    @ResponseStatus(HttpStatus.OK)
    public String uploadAvatar(MultipartFile file) throws IOException {
        //ToDO logic for storing avatar and user

        return userService.uploadAvatar(file);
    }

    @GetMapping("names/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public Set<UserDto> findUsersByName(
            @PathVariable @NotBlank String userName
    ) {
        return userService.findUsersByName(userName);
    }

    @GetMapping("emails/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByEmail(
            @PathVariable @NotBlank @Email String userEmail
    ) {
        return userService.findUserByEmail(userEmail);
    }

    @GetMapping("phones/{userPhoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByPhoneNumber(
            @PathVariable @NotBlank @Size(min = 12, max = 12)
            @Pattern(regexp = "^\\+?[0-9\\-\\s]+$") String userPhoneNumber
    ) {
        return userService.findUserByPhoneNumber(userPhoneNumber);
    }

    @GetMapping("exist/{userEmail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void isUserExist(
            @PathVariable @NotBlank @Email String userEmail
    ) {
        userService.isUserExistByEmail(userEmail);
    }

    @GetMapping("responded/vacancies/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findRespondedToVacancyUsersByVacancy(
            @PathVariable @Positive Long vacancyId
    ) {
        return userService.findRespondedToVacancyUsersByVacancy(vacancyId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @RequestParam @Positive Long userId, @RequestBody @Valid UserDto userDto
    ) {
        userService.updateUser(userId, userDto);
    }
}
