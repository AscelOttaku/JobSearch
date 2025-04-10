package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("users")
public class UserController {
    private final UserService userService;
    private final ResumeService resumeService;

    @Autowired
    public UserController(UserService userService, ResumeService resumeService) {
        this.userService = userService;
        this.resumeService = resumeService;
    }

    @GetMapping("profile/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String getProfile(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findUserById(userId);
        model.addAttribute("user", userDto);
        model.addAttribute("resumes", resumeService.findResumeByUserId(userDto.getUserId()));
        return "users/profile";
    }

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(@Valid UserDto userDto) {
        var userId = userService.createUser(userDto);
        return "redirect:/users/profile/" + userId;
    }

    @GetMapping("update/profile/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String editPage(@PathVariable Long userId, Model model) {
        UserDto userDto = userService.findUserById(userId);
        model.addAttribute("user", userDto);
        return "users/update_profile";
    }

    @GetMapping("job-seeker/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findJobSeekerByEmail(
            @PathVariable
            @Email(message = "{email_message}")
            String userEmail
    ) {
        return userService.findJobSeekerByEmail(userEmail);
    }

    @GetMapping("employer/{employerEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findEmployerByEmail(
            @PathVariable
            @Email(message = "{email_message}")
            String employerEmail
    ) {
        return userService.findEmployerByEmail(employerEmail);
    }

    @PutMapping("upload/avatars")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> uploadAvatar(MultipartFile file) throws IOException {
        return userService.uploadAvatar(file);
    }

    @GetMapping("names/{userName}")
    @ResponseStatus(HttpStatus.OK)
    public Set<UserDto> findUsersByName(
            @PathVariable
            @Pattern(
                    regexp = "^\\p{L}+$",
                    message = "{symbol_numbers_pattern_message}"
            )
            String userName
    ) {
        return userService.findUsersByName(userName);
    }

    @GetMapping("emails/{userEmail}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByEmail(
            @PathVariable
            @Email(message = "{email_message}")
            String userEmail
    ) {
        return userService.findUserByEmail(userEmail);
    }

    @GetMapping("phones/{userPhoneNumber}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserByPhoneNumber(
            @PathVariable
            @Pattern(regexp = "^\\+?[0-9\\-\\s]+$", message = "{phone_number_pattern_message}")
            String userPhoneNumber
    ) {
        return userService.findUserByPhoneNumber(userPhoneNumber);
    }

    @GetMapping("exist/{userEmail}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void isUserExist(
            @PathVariable
            @Email(message = "{email_message}")
            String userEmail
    ) {
        userService.isUserExistByEmail(userEmail);
    }

    @GetMapping("responded/vacancies/{vacancyId}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findRespondedToVacancyUsersByVacancy(@PathVariable Long vacancyId) {
        return userService.findRespondedToVacancyUsersByVacancy(vacancyId);
    }

    @PutMapping("updates")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @RequestBody @Valid UserDto userDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) throws IOException {
        userService.updateUser(userDto, userDetails);
    }

    @PutMapping("updates/non_authorized")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(UserDto userDto) {
        Long userId = userService.updateUser(userDto);
        return "redirect:/users/profile/".concat(String.valueOf(userId));
    }

    @GetMapping("avatars")
    public ResponseEntity<Object> getAvatars() throws IOException {
        return userService.getAvatarOfAuthorizedUser();
    }
}
