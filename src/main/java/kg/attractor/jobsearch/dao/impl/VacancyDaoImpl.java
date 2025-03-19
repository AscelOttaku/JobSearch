package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class VacancyDaoImpl implements VacancyDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Vacancy> vacancyRowMapper = new BeanPropertyRowMapper<>(Vacancy.class);

    @Override
    public List<Vacancy> findVacanciesByUserid(Long userid) {
        String query = "select * from VACANCIES " +
                "INNER JOIN RESPONDED_APPLICATION AS RA ON VACANCIES.ID = RA.VACANCY_ID " +
                "INNER JOIN RESUMES R ON R.ID = RA.RESUME_ID " +
                "INNER JOIN USERS U ON U.ID = R.USER_ID " +
                "WHERE U.ID = ?";

        return jdbcTemplate.query(query, vacancyRowMapper, userid);
    }

    @Override
    public List<Vacancy> findAllVacancies() {
        String query = "select * from VACANCIES";

        return jdbcTemplate.query(query, vacancyRowMapper);
    }

    @Override
    public List<Vacancy> findVacancyByCategory(Long categoryId) {
        String query = "SELECT * FROM VACANCIES WHERE CATEGORY_ID = ?";

        return jdbcTemplate.query(query, vacancyRowMapper, categoryId);
    }
}
