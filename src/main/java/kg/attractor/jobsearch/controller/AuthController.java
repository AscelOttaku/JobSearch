package kg.attractor.jobsearch.controller;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        model.addAttribute("token", token);
        return "auth/reset_password_form";
    }

    @PostMapping("/reset_password")
    public String processResetPassword(HttpServletRequest request, Model model) {
        String token = request.getParameter("token");
        String password = request.getParameter("password");

        User userByPasswordResetToken = userService.findUserByPasswordResetToken(token);
        userService.updatePassword(userByPasswordResetToken, password);
        model.addAttribute("message", "You have successfully changed your password.");
        return "auth/reset_password_message";
    }
}
