package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.dao.mapper.CustomerRowMapper;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class UserDao {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper userRowMapper;

    public Optional<User> findUserByEmail(String email) {
        String query = "select * from USERS where LOWER(EMAIL) = LOWER(?) limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, email));
    }

    public Optional<User> findUserByPhoneNumber(String phoneNumber) {
        String query = "select * from USERS where PHONE_NUMBER = ? limit 1";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, phoneNumber));
    }

    public List<User> findUsersByName(String name) {
        String query = "select * from USERS where LOWER(FIRST_NAME) LIKE LOWER(?)";
        return jdbcTemplate.query(query, userRowMapper, name.concat("%"));
    }

    public List<User> findRespondedToVacancyUsersByVacancy(Long vacancyId) {
        String query = """
                select\s
                    U.USER_ID,
                    U.FIRST_NAME,
                    U.SURNAME,
                    U.AGE,
                    U.EMAIL,
                    U.PASSWORD,
                    U.PHONE_NUMBER,
                    U.AVATAR,
                    U.ACCOUNT_TYPE
                    from USERS U
                                LEFT JOIN RESUMES AS R ON R.USER_ID = U.USER_ID
                                LEFT JOIN RESPONDED_APPLICATION AS RA ON RA.RESUME_ID = R.ID\s
                LEFT JOIN VACANCIES AS V ON V.ID = RA.
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

    public void createUser(User user) {
        String query = "insert into USERS (FIRST_NAME, SURNAME, AGE, EMAIL, PASSWORD, PHONE_NUMBER, ACCOUNT_TYPE, ENABLED, ROLE_ID)" +
                "values ( ?,?,?,?,?,?,?,?,? ) ";

        jdbcTemplate.update(
                query,
                user.getName(),
                user.getSurname(),
                user.getAge(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAccountType(),
                true,
                getUserRole(user.getAccountType())
                );
    }

    public Optional<User> findUserById(Long userId) {
        String query = "select * from USERS where USER_ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, userRowMapper, userId));
    }

    public void updateUser(User user) {
        String query = "update USERS AS U " +
                "SET U.FIRST_NAME = ?, " +
                "U.SURNAME = ?," +
                "U.EMAIL = ?," +
                "U.AGE = ?," +
                "U.AVATAR = ?," +
                "U.PASSWORD = ?," +
                "U.PHONE_NUMBER = ? " +
                "WHERE USER_ID = ?";

        jdbcTemplate.update(
                query,
                user.getName(),
                user.getSurname(),
                user.getEmail(),
                user.getAge(),
                user.getAvatar(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getUserId()
        );
    }

    public List<User> findAllUsers() {
        String query = "select * from USERS";

        return jdbcTemplate.query(query, userRowMapper);
    }

    public Long getUserRole(String accountType) {
        String query = "select id from ROLES where LOWER(ROLE) = ?";

        String accountTypeInSneakyCase = Util.convertToSneakyCase(accountType);

        return handleDataAccessException(() ->
                jdbcTemplate.queryForObject(query, Long.class, accountTypeInSneakyCase))
                .orElse(-1L);
    }

    public void uploadAvatarFile(String userEmail, String fileName) {
        String query = "update USERS " +
                "set USERS.AVATAR = ?" +
                "where EMAIL = ? ";

        jdbcTemplate.update(query, fileName, userEmail);
    }

    public Optional<String> findPasswordByUserId(Long userId) {
        String query = "select PASSWORD from users where USER_ID = ?";

        return handleDataAccessException(() ->
                jdbcTemplate.queryForObject(query, String.class, userId));
    }

    public Optional<Long> findUserIdByEmail(String email) {
        String query = "select USER_ID from USERS where EMAIL ilike ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, Long.class, email));
    }
}
