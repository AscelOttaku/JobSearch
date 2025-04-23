package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.enums.Roles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .defaultSuccessUrl("/users/profile")
                        .failureUrl("/login?error=true")
                        .permitAll())

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .permitAll())

                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                //Vacancies Endpoints

                                .requestMatchers(GET, "/vacancies/new_vacancy").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(GET, "/vacancies/update/vacancy/*").hasAnyAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(PUT, "/vacancies/redactor-vacancies").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(DELETE, "/vacancies/delete_vacancies").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/vacancies/users/responded_vacancies").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(POST, "/vacancies/times").fullyAuthenticated()

                                // Users

                                .requestMatchers(PUT, "/users/updates").hasAnyAuthority(Roles.EMPLOYER.getValue(), Roles.JOB_SEEKER.getValue())
                                .requestMatchers(POST, "/users/registration").anonymous()
                                .requestMatchers(GET, "/users/profile").authenticated()
                                .requestMatchers("users/update/profile").fullyAuthenticated()
                                .requestMatchers("/users/responded/vacancies/*").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/users/employer/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers("/users/job-seeker/*").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/users/upload/*").fullyAuthenticated().requestMatchers("/users/avatars").fullyAuthenticated()
                                .requestMatchers("/users/profile/*").fullyAuthenticated()
                                .requestMatchers(GET,"/users/**").hasAuthority(Roles.EMPLOYER.getValue())

                                // Companies

                                .requestMatchers(GET, "/companies").hasAuthority(Roles.JOB_SEEKER.getValue())

//                                RespondedApplication Endpoints

                                .requestMatchers(POST, "/responds")
                                .hasAuthority(Roles.JOB_SEEKER.getValue())

//                                Resumes Endpoints

                                .requestMatchers(GET, "/resumes").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(POST, "/resumes").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(PUT, "/resumes/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(DELETE, "/resumes/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(GET, "/resumes/update/resume/*") .hasAnyAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers("/resumes/*").authenticated()

                                //All Other Endpoints

                                .requestMatchers("/static/css/*")
                                .permitAll()

                                .anyRequest().permitAll());

        return http.build();
    }
}