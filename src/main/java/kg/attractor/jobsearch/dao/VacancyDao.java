package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class VacancyDao {
    private final JdbcTemplate jdbcTemplate;

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
                "VACANCIES.VACANCY_USER_ID," +
                "VACANCIES.CREATED," +
                "VACANCIES.UPDATED " +
                "from VACANCIES " +
                "INNER JOIN RESPONDED_APPLICATION AS RA ON VACANCIES.ID = RA.VACANCY_ID " +
                "INNER JOIN RESUMES R ON R.ID = RA.RESUME_ID " +
                "INNER JOIN USERS U ON U.USER_ID = R.USER_ID " +
                "WHERE U.EMAIL = ?";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Vacancy.class), userEmail);
    }

    public List<Vacancy> findAllVacancies() {
        String query = "select * from VACANCIES";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public List<Vacancy> findVacancyByCategory(Long categoryId) {
        String query = "SELECT * FROM VACANCIES WHERE CATEGORY_ID = ?";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Vacancy.class), categoryId);
    }

    public Optional<Vacancy> findVacancyById(Long vacancyId) {
        String query = "SELECT * FROM VACANCIES WHERE ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, new BeanPropertyRowMapper<>(Vacancy.class), vacancyId));
    }

    public boolean deleteVacancyById(Long vacancyId) {
        String query = "DELETE FROM VACANCIES WHERE ID = ?";

        return jdbcTemplate.update(query, vacancyId) > 0;
    }

    public List<Vacancy> findAllActiveVacancies() {
        String query = "select * from VACANCIES WHERE IS_ACTIVE = TRUE";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Vacancy.class));
    }

    public Long createVacancy(Vacancy vacancy) {
        String query = """
                INSERT INTO VACANCIES(NAME, DESCRIPTION, CATEGORY_ID, SALARY, EXP_FROM, EXP_TO, VACANCY_USER_ID)
                values ( ?,?,?,?,?,?,?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setString(1, vacancy.getName());
            pr.setString(2, vacancy.getDescription());
            pr.setLong(3, vacancy.getCategoryId());
            pr.setDouble(4, vacancy.getSalary());
            pr.setDouble(5, vacancy.getExpFrom());
            pr.setDouble(6, vacancy.getExpTo());
            pr.setLong(7, vacancy.getUserId());
            return pr;
        }, keyHolder);

        Number key = keyHolder.getKey();
        return key != null ? key.longValue() : null;
    }

    public Long updateVacancy(Vacancy vacancy) {
        String query = """
                UPDATE VACANCIES
                SET NAME = ?, DESCRIPTION = ?, CATEGORY_ID = ?, SALARY = ?, EXP_FROM = ?, EXP_TO = ?, IS_ACTIVE = ?
                WHERE ID = ?""";

        return jdbcTemplate.update(
                query,
                vacancy.getName(),
                vacancy.getDescription(),
                vacancy.getCategoryId(),
                vacancy.getSalary(),
                vacancy.getExpFrom(),
                vacancy.getExpTo(),
                vacancy.getIsActive(),
                vacancy.getId()
        ) > 0 ? vacancy.getId() : -1L;
    }
}
