package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public Optional<User> findUserByEmail(String email) {
        String query = "select * from USERS where EMAIL = ? limit 1";

        return Optional.ofNullable(
                jdbcTemplate.queryForObject(query, userRowMapper, email)
        );
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        String query = "select * from USERS where PHONE_NUMBER = ? limit 1";

        return Optional.ofNullable(jdbcTemplate.queryForObject(query, userRowMapper, phoneNumber));
    }

    @Override
    public List<User> findUsersByName(String name) {
        String query = "select * from USERS where NAME = ?";

        return jdbcTemplate.query(query, userRowMapper, name);
    }

    @Override
    public List<User> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        String query = "select * from USERS " +
                "INNER JOIN RESUMES AS R ON R.USER_ID = USERS.ID " +
                "INNER JOIN RESPONDED_APPLICATION AS RA ON RA.RESUME_ID = R.ID " +
                "INNER JOIN VACANCIES AS V ON V.ID = RA.VACANCY_ID " +
                "WHERE V.ID = ?";

        return jdbcTemplate.query(query, userRowMapper, vacancyId);
    }
}
