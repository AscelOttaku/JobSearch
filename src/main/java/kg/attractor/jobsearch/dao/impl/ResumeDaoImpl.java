package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ResumeDaoImpl implements ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Resume> resumeMapper = new BeanPropertyRowMapper<>(Resume.class);

    @Override
    public List<Resume> findResumesByCategory(Category category) {
        String query = "select * from RESUMES where CATEGORY_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, category.getId());
    }

    @Override
    public List<Resume> findUserCreatedResumes(Long userid) {
        String query = "select * from RESUMES where USER_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, userid);
    }
}
