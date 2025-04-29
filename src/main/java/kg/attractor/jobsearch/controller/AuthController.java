package kg.attractor.jobsearch.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.PasswordToken;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("login")
    public String login(@RequestParam(required = false) Boolean error, Model model) {
        if (error != null && error) {
            model.addAttribute("status", HttpStatus.NOT_ACCEPTABLE.value());
            model.addAttribute("reason", HttpStatus.NOT_ACCEPTABLE.getReasonPhrase());
            model.addAttribute("message", "Login error password or email is incorrect");
            return "errors/error";
        }

        model.addAttribute("user", new UserDto());
        return "auth/login";
    }

    @GetMapping("registration")
    public String registerForm(Model model) {
        UserDto userDto = new UserDto();
        userDto.setPhoneNumber("+996");
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }

    @GetMapping("forgot_password")
    public String showForgotPasswordForm() {
        return "auth/forgot_password_form";
    }

    @PostMapping("/forgot_password")
    public String processForgotPassword(HttpServletRequest request, Model model) {
        try {
            userService.makeResetPasswordLink(request);
            model.addAttribute("message", "Reset password link was sent to this email. Please check.");
        } catch (MessagingException e) {
            model.addAttribute("error", e.getMessage());
        }
        return "auth/forgot_password_form";
    }

    @GetMapping("/reset_password")
    public String showResetPasswordForm(@RequestParam String token, Model model) {
        userService.findUserByPasswordResetToken(token);
        model.addAttribute("password_token", PasswordToken.builder()
                .token(token)
                .build());

        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(
            @ModelAttribute("password_token") @Valid PasswordToken passwordToken,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("password_token", passwordToken);
            return "auth/reset_password_form";
        }

        User userByPasswordResetToken = userService.findUserByPasswordResetToken(passwordToken.getToken());
        userService.updatePassword(userByPasswordResetToken, passwordToken.getPassword());
        model.addAttribute("message", "You have successfully changed your password.");
        return "auth/reset_password_message";
    }
}
