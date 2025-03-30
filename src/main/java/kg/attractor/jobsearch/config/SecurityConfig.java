package kg.attractor.jobsearch.config;

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
                                .requestMatchers("/vacancies/new-vacancies")
                                .hasAuthority("EMPLOYER")

                                .requestMatchers("/vacancies/redactor-vacancies")
                                .hasAuthority("EMPLOYER")

                                .requestMatchers("/vacancies/delete_vacancies/")
                                .hasAuthority("EMPLOYER")

                                .requestMatchers("/vacancies/users/responded_vacancies")
                                .fullyAuthenticated()

                                .requestMatchers("/users/updates")
                                .fullyAuthenticated()

                                .requestMatchers("users/responded/vacancies/")
                                .hasAuthority("EMPLOYER")

                                .requestMatchers("users/exist/*")
                                .anonymous()

                                .requestMatchers("/users/employer")
                                .hasAuthority("JOB_SEEKER")

                                .requestMatchers("users/job-seeker")
                                .hasAuthority("EMPLOYER")

                                .requestMatchers("users/**")
                                .fullyAuthenticated()

                                .anyRequest().permitAll());

        return http.build();
    }
}
