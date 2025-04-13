package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);

    public Optional<Category> findCategoryById(Long vacancyId) {
        String query = "select * from CATEGORIES where ID = ?";

        return handleDataAccessException(() ->
                jdbcTemplate.queryForObject(query, rowMapper, vacancyId));
    }

    public List<Category> findAllCategories() {
        String query = "select * from CATEGORIES";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Category.class));
    }

    public Optional<String> findCategoryNameById(Long categoryId) {
        String query = "select NAME from CATEGORIES where ID = ?";

        return handleDataAccessException(() ->
                jdbcTemplate.queryForObject(query, String.class, categoryId));
    }
}
