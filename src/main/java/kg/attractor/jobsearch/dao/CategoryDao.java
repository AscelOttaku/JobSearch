package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Category> rowMapper = new BeanPropertyRowMapper<>(Category.class);

    public Category findCategoryById(Long vacancyId) {
        String query = "select * from CATEGORIES where ID = ?";

        return jdbcTemplate.queryForObject(query, rowMapper, vacancyId);
    }

    public List<Category> findAllCategories() {
        String query = "select * from CATEGORIES";

        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(Category.class));
    }
}
