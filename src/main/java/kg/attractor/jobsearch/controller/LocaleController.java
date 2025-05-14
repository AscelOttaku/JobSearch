package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@RequestMapping("api")
public class LocaleController {
    private final LocaleResolver localeResolver;

    public LocaleController(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @PostMapping("locale")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String setLocaleResolver(HttpServletRequest request, HttpServletResponse response) {
        String lang = request.getParameter("lang");
        String previousUrl = request.getHeader("Referer");

        if (lang == null || lang.isBlank())
            return "redirect:".concat(previousUrl);

        Locale locale = Locale.of(lang);
        localeResolver.setLocale(request, response, locale);
        return "redirect:".concat(previousUrl);
    }
}
