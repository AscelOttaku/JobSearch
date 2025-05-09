package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kg.attractor.jobsearch.interceptor.CustomLocaleResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Controller
@RequestMapping("api/lang")
@RequiredArgsConstructor
public class LocaleController {
    private final CustomLocaleResolver customLocaleResolver;
    private final LocaleResolver localeResolver;

    @GetMapping
    public String changeLanguage(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        Locale locale = customLocaleResolver.resolveLocale(request);
        customLocaleResolver.setLocale(request, response, locale);
        localeResolver.setLocale(request, response, locale);

        String previousUrl = request.getHeader("Referer");
        return "redirect:" + previousUrl;
    }
}
