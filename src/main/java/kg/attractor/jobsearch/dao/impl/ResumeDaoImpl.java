package kg.attractor.jobsearch.dao.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.model.Resume;
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
    public List<Resume> findResumesByCategory(Long categoryId) {
        String query = "select * from RESUMES where CATEGORY_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, categoryId);
    }

    @Override
    public List<Resume> findUserCreatedResumes(Long userid) {
        String query = "select * from RESUMES where USER_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, userid);
    }

    @Override
    public List<Resume> findAllResumes() {
        String query = "select * from RESUMES";

        return jdbcTemplate.query(query, resumeMapper);
    }
}
