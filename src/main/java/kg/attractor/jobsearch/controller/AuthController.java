package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/login";
    }

    @GetMapping("forbidden")
    public String forbidden() {
        return "auth/forbidden";
    }

    @GetMapping("registration")
    public String registerForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/register";
    }
}
