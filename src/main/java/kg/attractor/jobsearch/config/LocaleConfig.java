package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.locale.CustomLocaleResolver;
import kg.attractor.jobsearch.repository.UsersLocaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class LocaleConfig implements WebMvcConfigurer {

//    @Bean
//    public LocaleResolver localeResolver(UsersLocaleRepository usersLocaleRepository) {
//        return new CustomLocaleResolver(usersLocaleRepository);
//    }
}
