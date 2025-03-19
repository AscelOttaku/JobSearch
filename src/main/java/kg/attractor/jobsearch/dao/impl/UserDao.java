package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.Dao;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDao implements Dao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public Optional<User> findUserByEmail(String email) {
        String query = "select * from users where EMAIL = ?";

        return Optional.ofNullable(
                jdbcTemplate.queryForObject(query, userRowMapper, email)
        );
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        String query = "select * from users where PHONE_NUMBER = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, phoneNumber));
    }

    @Override
    public Optional<User> findUserByName(String name) {
        String query = "select * from users where NAME = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, name));
    }
}
