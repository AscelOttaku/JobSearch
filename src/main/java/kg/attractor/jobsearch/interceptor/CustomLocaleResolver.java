package kg.attractor.jobsearch.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import kg.attractor.jobsearch.model.UsersLocale;
import kg.attractor.jobsearch.repository.UsersLocaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class CustomLocaleResolver implements LocaleResolver {
    private final UsersLocaleRepository usersLocaleRepository;

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String lang = request.getParameter("lang");
        Locale locale = lang != null ? Locale.of(lang) : null;

        HttpSession session = request.getSession();

        if (locale == null && session != null)
            locale = (Locale) session.getAttribute("lang");

        if (locale == null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            boolean isUserAuth = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);

            return isUserAuth ?
                    usersLocaleRepository.findUsersLocaleByUserEmail(authentication.getName())
                            .map(usersLocale -> Locale.of(usersLocale.getLocale()))
                            .orElse(Locale.ENGLISH) : Locale.ENGLISH;
        } else
            return locale;
    }

    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        HttpSession session = request.getSession();

        if (authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken)) {
            UsersLocale usersLocale = new UsersLocale();
            usersLocale.setUserEmail(authentication.getName());
            usersLocale.setLocale(locale.getLanguage());
            usersLocaleRepository.save(usersLocale);
        }

        session.setAttribute("lang", locale);
    }
}
