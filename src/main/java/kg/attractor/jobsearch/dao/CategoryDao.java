package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.util.ExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);

    public Optional<Category> findCategoryById(Long vacancyId) {
        String query = "select * from VACANCIES where VACANCIES.ID = ?";

        return ExceptionHandler.handleDataAccessException(() -> jdbcTemplate.queryForObject(query, rowMapper, vacancyId));
    }
}
