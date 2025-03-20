package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class UserDaoImpl implements UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    @Override
    public Optional<User> findUserByEmail(String email) {
        String query = "select * from USERS where LOWER(EMAIL) = LOWER(?) limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    @Override
    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        String query = "select * from USERS where PHONE_NUMBER = ? limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, phoneNumber));
    }

    @Override
    public List<User> findUsersByName(String name) {
        String query = "select * from USERS where LOWER(NAME) LIKE LOWER(?)";
        return jdbcTemplate.query(query, userRowMapper, name.concat("%"));
    }

    @Override
    public List<User> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        String query = "SELECT \n" +
                "    USERS.ID AS id," +
                "    USERS.NAME AS NAME,"+
                "    USERS.SURNAME AS SURNAME,"+
                "    USERS.AGE,"+
                "    USERS.EMAIL AS EMAIL," +
                "    USERS.PASSWORD AS PASSWORD, " +
                "    USERS.PHONE_NUMBER, " +
                "    USERS.AVATAR, " +
                "    USERS.ACCOUNT_TYPE " +
                "FROM USERS " +
                "INNER JOIN RESUMES AS R ON R.USER_ID = USERS.ID " +
                "INNER JOIN RESPONDED_APPLICATION AS RA ON RA.RESUME_ID = R.ID " +
                "INNER JOIN VACANCIES AS V ON V.ID = RA.VACANCY_ID " +
                "WHERE V.ID = ? AND USERS.ACCOUNT_TYPE LIKE 'JobSeeker'" +
                "AND RA.CONFIRMATION = TRUE";

        return jdbcTemplate.query(query, userRowMapper, vacancyId);
    }

    @Override
    public Optional<User> findJobSeekerByEmail(String email) {
        String query = "select * from USERS where lower(EMAIL) LIKE LOWER(?) " +
                "AND USERS.ACCOUNT_TYPE LIKE 'JobSeeker' LIMIT 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    @Override
    public Optional<User> findEmployerByEmail(String email) {
        String query = "select * from USERS where LOWER(EMAIL) LIKE LOWER(?) " +
                "AND USERS.ACCOUNT_TYPE LIKE 'Employer' LIMIT 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }
}
