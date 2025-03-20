package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Vacancy> vacancyRowMapper = new BeanPropertyRowMapper<>(Vacancy.class);

    public List<Vacancy> findVacanciesByUserEmail(String userEmail) {
        String query = "select DISTINCT " +
                "VACANCIES.ID," +
                "VACANCIES.NAME," +
                "VACANCIES.DESCRIPTION," +
                "VACANCIES.CATEGORY_ID," +
                "VACANCIES.SALARY," +
                "VACANCIES.EXP_FROM," +
                "VACANCIES.EXP_TO," +
                "VACANCIES.IS_ACTIVE," +
                "VACANCIES.USER_ID," +
                "VACANCIES.CREATED," +
                "VACANCIES.UPDATED " +
                "from VACANCIES " +
                "INNER JOIN RESPONDED_APPLICATION AS RA ON VACANCIES.ID = RA.VACANCY_ID " +
                "INNER JOIN RESUMES R ON R.ID = RA.RESUME_ID " +
                "INNER JOIN USERS U ON U.USERID = R.USER_ID " +
                "WHERE U.EMAIL = ?";

        return jdbcTemplate.query(query, vacancyRowMapper, userEmail);
    }

    public List<Vacancy> findAllVacancies() {
        String query = "select * from VACANCIES";

        return jdbcTemplate.query(query, vacancyRowMapper);
    }

    public List<Vacancy> findVacancyByCategory(Long categoryId) {
        String query = "SELECT * FROM VACANCIES WHERE CATEGORY_ID = ?";

        return jdbcTemplate.query(query, vacancyRowMapper, categoryId);
    }

    public Optional<Vacancy> findVacancyById(Long vacancyId) {
        String query = "SELECT * FROM VACANCIES WHERE ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, vacancyRowMapper, vacancyId));
    }

    public boolean deleteVacancyById(Long vacancyId) {
        String query = "DELETE FROM VACANCIES WHERE ID = ?";

        return jdbcTemplate.update(query, vacancyId) > 0;
    }

    public List<Vacancy> findAllActiveVacancies() {
        String query = "select * from VACANCIES WHERE IS_ACTIVE = TRUE";

        return jdbcTemplate.query(query, vacancyRowMapper);
    }

    public Long createVacancy(Vacancy vacancy) {
        String query = """
                INSERT INTO VACANCIES(NAME, DESCRIPTION, CATEGORY_ID, SALARY, EXP_FROM, EXP_TO, IS_ACTIVE, USER_ID)
                values ( ?,?,?,?,?,?,?,?)
                """;

        KeyHolder keyHolder = createData(vacancy, query);

        Number number = keyHolder.getKey();
        return number != null ? number.longValue() : -1;
    }

    public Long updateVacancy(Vacancy vacancy) {
        String query = """
                UPDATE VACANCIES
                SET NAME = ?, DESCRIPTION = ?, CATEGORY_ID = ?, SALARY = ?, EXP_FROM = ?, EXP_TO = ?, IS_ACTIVE = ?, USER_ID = ?
                WHERE ID = ?""";

        KeyHolder keyHolder = updateData(vacancy, query);

        Number number = keyHolder.getKey();
        return number != null ? number.longValue() : -1;
    }

    private KeyHolder createData(Vacancy vacancy, String query) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection ->{
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            return setPreparedStatement(vacancy, preparedStatement, false);
        }, keyHolder);
        return keyHolder;
    }

    private KeyHolder updateData(Vacancy vacancy, String query) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            return setPreparedStatement(vacancy, preparedStatement, true);
        }, keyHolder);

        return keyHolder;
    }

    private static PreparedStatement setPreparedStatement(
            Vacancy vacancy, PreparedStatement pr, boolean isUpdate
    ) throws SQLException {

        pr.setString(1, vacancy.getName());
        pr.setString(2, vacancy.getDescription());
        pr.setLong(3, vacancy.getCategoryId());
        pr.setDouble(4, vacancy.getSalary());
        pr.setDouble(5, vacancy.getExpFrom());
        pr.setDouble(6, vacancy.getExpTo());
        pr.setBoolean(7, vacancy.getIsActive());
        pr.setLong(8, vacancy.getUserId());

        if (isUpdate)
            pr.setLong(9, vacancy.getId());

        return pr;
    }
}
