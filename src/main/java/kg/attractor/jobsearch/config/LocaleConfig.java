package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.interceptor.CustomLocalInterceptor;
import kg.attractor.jobsearch.interceptor.CustomLocaleResolver;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.repository.UsersLocaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@RequiredArgsConstructor
public class LocaleConfig implements WebMvcConfigurer {
    private final UsersLocaleRepository usersLocaleRepository;

    @Bean
    public LocaleResolver localeResolver() {
        return new CustomLocaleResolver(usersLocaleRepository);
    }

    public CustomLocalInterceptor customLocalInterceptor() {
        return new CustomLocalInterceptor(localeResolver());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customLocalInterceptor());
    }
}
