package kg.attractor.jobsearch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectToActiveVacancies() {
        return "redirect:/vacancies/actives";
    }

    @GetMapping("/search")
    public String searchPage() {
        return "search/search";
    }
}
