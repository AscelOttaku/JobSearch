package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<User> userRowMapper = new BeanPropertyRowMapper<>(User.class);

    public Optional<User> findUserByEmail(String email) {
        String query = "select * from USERS where LOWER(EMAIL) = LOWER(?) limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        String query = "select * from USERS where PHONE_NUMBER = ? limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, phoneNumber));
    }

    public List<User> findUsersByName(String name) {
        String query = "select * from USERS where LOWER(FIRSTNAME) LIKE LOWER(?)";
        return jdbcTemplate.query(query, userRowMapper, name.concat("%"));
    }

    public List<User> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        String query = """
                select * from USERS U
                                INNER JOIN RESUMES AS R ON R.USER_ID = U.USERID
                                INNER JOIN RESPONDED_APPLICATION AS RA ON RA.RESUME_ID = R.ID\s
                RIGHT JOIN VACANCIES AS V ON V.ID = RA.
                        VACANCY_ID\s
                WHERE V.ID = ? AND U.ACCOUNT_TYPE LIKE 'JobSeeker';
               \s""";

        return jdbcTemplate.query(query, userRowMapper, vacancyId);
    }

    public Optional<User> findJobSeekerByEmail(String email) {
        String query = "select * from USERS where lower(EMAIL) LIKE LOWER(?) " +
                "AND USERS.ACCOUNT_TYPE LIKE 'JobSeeker' LIMIT 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    public Optional<User> findEmployerByEmail(String email) {
        String query = "select * from USERS where LOWER(EMAIL) LIKE LOWER(?) " +
                "AND USERS.ACCOUNT_TYPE LIKE 'Employer' LIMIT 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    public Long createUser(User user) {
        String query = "insert into USERS (FIRSTNAME, SURNAME, AGE, EMAIL, PASSWORD, PHONE_NUMBER, AVATAR, ACCOUNT_TYPE)" +
                "values ( ?,?,?,?,?,?,?,? ) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setInt(3, user.getAge());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getPhoneNumber());
            ps.setString(7, user.getAvatar());
            ps.setString(8, user.getAccountType());
            return ps;
        }, keyHolder);

        Number number = keyHolder.getKey();
        return number != null ? number.longValue() : -1;
    }
}
