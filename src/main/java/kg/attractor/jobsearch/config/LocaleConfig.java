package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.interceptor.CustomLocalInterceptor;
import kg.attractor.jobsearch.interceptor.CustomLocaleResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class LocaleConfig implements WebMvcConfigurer {
    private final CustomLocaleResolver customLocaleResolver;

    public LocaleConfig(CustomLocaleResolver customLocaleInterceptor) {
        this.customLocaleResolver = customLocaleInterceptor;
    }

    @Bean
    public org.springframework.web.servlet.LocaleResolver localeResolver() {
        return customLocaleResolver;
    }

    @Bean
    public CustomLocalInterceptor customLocalInterceptor() {
        return new CustomLocalInterceptor(customLocaleResolver);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(customLocalInterceptor());
    }
}
