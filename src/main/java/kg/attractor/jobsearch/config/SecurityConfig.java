package kg.attractor.jobsearch.config;

import kg.attractor.jobsearch.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JdbcUserDetailsManager configureGlobal(DataSource dataSource) {
        String usersQuery = "select email, password, enabled from users " +
                "where email = ?";

        String authorityQuery = "select email, ROLE from USERS U, ROLES R " +
                "INNER JOIN ROLES_AUTHORITIES RA ON RA.ROLE_ID = R.ID " +
                "WHERE U.ROLE_ID = R.ID AND EMAIL = ?";

        JdbcUserDetailsManager userDetailsManager = new JdbcUserDetailsManager(dataSource);
        userDetailsManager.setUsersByUsernameQuery(usersQuery);
        userDetailsManager.setAuthoritiesByUsernameQuery(authorityQuery);
        return userDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .httpBasic(Customizer.withDefaults())

                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                //Vacancies Endpoints

                                .requestMatchers(POST, "/vacancies/new-vacancies")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers(PUT, "/vacancies/redactor-vacancies")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers(DELETE, "/vacancies/delete_vacancies")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers("/vacancies/users/responded_vacancies")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                // Users Endpoints

                                .requestMatchers(POST, "/users/updates")
                                .fullyAuthenticated()

                                .requestMatchers("/users/responded/vacancies/*")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers("/users/employer/*")
                                .hasAuthority(Role.JOB_SEEKER.getValue())

                                .requestMatchers("/users/job-seeker/*")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers("/users/upload/*")
                                .fullyAuthenticated()

                                .requestMatchers("/users/**")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                //RespondedApplication Endpoints

                                .requestMatchers(POST, "/responds")
                                .hasAuthority(Role.JOB_SEEKER.getValue())

                                //Resumes Endpoints

                                .requestMatchers(GET, "/resumes")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                .requestMatchers(POST, "/resumes")
                                .hasAuthority(Role.JOB_SEEKER.getValue())

                                .requestMatchers(PUT, "/resumes/*")
                                .hasAuthority(Role.JOB_SEEKER.getValue())

                                .requestMatchers(DELETE, "/resumes/*")
                                .hasAuthority(Role.JOB_SEEKER.getValue())

                                .requestMatchers("/resumes/*")
                                .hasAuthority(Role.EMPLOYER.getValue())

                                //All Other Endpoints

                                .anyRequest().permitAll());

        return http.build();
    }
}
