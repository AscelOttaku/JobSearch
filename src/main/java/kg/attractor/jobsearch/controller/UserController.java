package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.ProfileService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final AuthorizedUserService authorizedUserService;
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public String getProfile(Model model) {
        model.addAllAttributes(profileService.getProfile());
        return "users/profile";
    }

    @PostMapping("registration")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createUser(@Valid UserDto userDto) {
        userService.createUser(userDto);
        return "redirect:/users/profile";
    }

    @GetMapping("update/profile")
    @ResponseStatus(HttpStatus.OK)
    public String editPage(Model model) {
        UserDto userDto = userService.findUserById(authorizedUserService.getAuthorizedUserId());
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

    @PostMapping("upload/avatars")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> uploadAvatar(MultipartFile file) throws IOException {
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
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(
            @ModelAttribute @Valid UserDto userDto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        userService.updateUser(userDto, userDetails);
        return "redirect:/users/profile";
    }

    @GetMapping("avatars")
    public ResponseEntity<?> getAvatars() throws IOException {
        return userService.getAvatarOfAuthorizedUser();
    }
}
