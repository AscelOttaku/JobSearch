package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final AuthorizedUserService authorizedUserService;
    private final UserService userService;
    private final ProfileService profileService;

    @GetMapping("profile")
    @ResponseStatus(HttpStatus.OK)
    public String getProfile(
            Model model,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "size", defaultValue = "10", required = false) Integer size
    ) {
        model.addAllAttributes(profileService.getProfile(page, size));
        return "users/profile";
    }

    @GetMapping("profile/statistics")
    public String getUserStatistics(Model model) {
        model.addAllAttributes(profileService.getStatistics());
        return "users/statistics";
    }

    @PostMapping("registration")
    public String createUser(
            @Valid @ModelAttribute("user") UserDto userDto,
            BindingResult bindingResult,
            Model model,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "/auth/register";
        }

        userService.createUser(userDto, request);
        return "redirect:/users/profile";
    }

    @GetMapping("/update/profile")
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
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String uploadAvatar(MultipartFile file) throws IOException {
        userService.uploadAvatar(file);
        return "redirect:/users/profile";
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

    @PostMapping("/updates/profile")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String updateUser(
            @ModelAttribute("user") @Valid UserDto userDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDto);
            return "users/update_profile";
        }

        userService.updateUser(userDto);
        return "redirect:/users/profile";
    }

    @GetMapping("avatars")
    public ResponseEntity<?> getAvatars() throws IOException {
        return userService.getAvatarOfAuthorizedUser();
    }

    @GetMapping("avatar/{avatar}")
    public ResponseEntity<?> getAvatar(@PathVariable String avatar) throws IOException {
        return userService.getAvatar(avatar);
    }

    @GetMapping("profile/{userId}")
    public String getUserInfo(@PathVariable Long userId, Model model) {
        model.addAttribute("job_seeker", userService.findUserById(userId));
        return "users/contact_info";
    }
}
