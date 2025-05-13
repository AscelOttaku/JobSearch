package kg.attractor.jobsearch.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class CustomLocalInterceptor implements HandlerInterceptor {
    private final LocaleResolver localeResolver;

    public CustomLocalInterceptor(LocaleResolver localeResolver) {
        this.localeResolver = localeResolver;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String lang = request.getParameter("lang");

        if (lang == null)
            return true;

        Locale locale = Locale.of(lang);
        localeResolver.setLocale(request, response, locale);
        return true;
    }
}
