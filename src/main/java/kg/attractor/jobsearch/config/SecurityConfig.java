package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.enums.Roles;
import kg.attractor.jobsearch.model.CustomOAuth2User;
import kg.attractor.jobsearch.security.MySimpleAuthenticationHandler;
import kg.attractor.jobsearch.service.impl.AuthUserDetailsServiceImpl;
import kg.attractor.jobsearch.service.impl.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final AuthUserDetailsServiceImpl userDetailsService;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new MySimpleAuthenticationHandler();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/auth/login")
                        .successHandler(authenticationSuccessHandler())
                        .failureUrl("/auth/login?error=true")
                        .permitAll())

                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/auth/logout"))
                        .permitAll())

                .httpBasic(Customizer.withDefaults())

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                //Vacancies Endpoints

                                .requestMatchers(POST, "/vacancies/times?vacancyId=**").hasAnyAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(GET, "/vacancies/new_vacancy").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(GET, "/vacancies/update/vacancy/*").hasAnyAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(PUT, "/vacancies/redactor-vacancies").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(DELETE, "/vacancies/delete_vacancies").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/vacancies/users/responded_vacancies").hasAuthority(Roles.EMPLOYER.getValue())

                                // Users

                                .requestMatchers(PUT, "/users/updates").hasAnyAuthority(Roles.EMPLOYER.getValue(), Roles.JOB_SEEKER.getValue())
                                .requestMatchers(POST, "/users/registration").anonymous()
                                .requestMatchers(GET, "/users/profile").authenticated()
                                .requestMatchers("/users/update/profile").fullyAuthenticated()
                                .requestMatchers(POST, "/users/upload/avatars").hasAnyAuthority(Roles.EMPLOYER.getValue(), Roles.JOB_SEEKER.getValue())
                                .requestMatchers("/users/responded/vacancies/*").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/users/employer/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers("/users/job-seeker/*").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers("/users/upload/*").fullyAuthenticated().requestMatchers("/users/avatars").fullyAuthenticated()
                                .requestMatchers("/users/profile/*").fullyAuthenticated()
                                .requestMatchers(GET, "/users/avatar/*").permitAll()
                                .requestMatchers(GET,"/users/**").hasAuthority(Roles.EMPLOYER.getValue())

                                // Companies

                                 .requestMatchers(GET, "/companies").hasAuthority(Roles.JOB_SEEKER.getValue())

//                                RespondedApplication Endpoints

                                .requestMatchers(POST, "/responds").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(GET, "/responds/users").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(GET, "responds/employers").hasAuthority(Roles.EMPLOYER.getValue())

                                 //Resumes Endpoints

                                .requestMatchers(GET, "/resumes").hasAuthority(Roles.EMPLOYER.getValue())
                                .requestMatchers(POST, "/resumes").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(PUT, "/resumes/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(DELETE, "/resumes/*").hasAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(GET, "/resumes/update/resume/*") .hasAnyAuthority(Roles.JOB_SEEKER.getValue())
                                .requestMatchers(GET, "/resumes/users/actives").fullyAuthenticated()
                                .requestMatchers("/resumes/*").authenticated()

                                //Favorites

                                .requestMatchers(POST, "/favorites").fullyAuthenticated()
                                .requestMatchers(POST, "/favorites/delete/*").fullyAuthenticated()

                                //Messages

                                .requestMatchers(POST, "/messages").authenticated()
                                .requestMatchers(GET, "/messages/*").authenticated()

                                //All Other Endpoints

                                .requestMatchers("/static/css/*").permitAll()

                                .anyRequest().permitAll())

                // OAuth2 config

                .oauth2Login(oauth -> oauth
                        .loginPage("/auth/login")
                        .userInfoEndpoint(userConfig -> userConfig
                                .userService(customOAuth2UserService))
                        .successHandler((request, response, authentication) -> {
                            var oathUser = (CustomOAuth2User) authentication.getPrincipal();
                            userDetailsService.processOAuthPostLogin(oathUser.getAttributes());
                            response.sendRedirect("/users/profile");
                        }));

        return http.build();
    }
}