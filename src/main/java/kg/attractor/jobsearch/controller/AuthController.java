package kg.attractor.jobsearch.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("forbidden")
    public String forbidden() {
        return "auth/forbidden";
    }

    @GetMapping("registration")
    public String registerForm() {
        return "auth/register";
    }
}
