package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class ResumeDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<Resume> resumeMapper = new BeanPropertyRowMapper<>(Resume.class);
    private final KeyHolder keyHolder = new GeneratedKeyHolder();

    public List<Resume> findResumesByCategory(Long categoryId) {
        String query = "select * from RESUMES where CATEGORY_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, categoryId);
    }

    public List<Resume> findUserCreatedResumes(Long userid) {
        String query = "select * from RESUMES where USER_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, userid);
    }

    public List<Resume> findAllResumes() {
        String query = "select * from RESUMES";

        return jdbcTemplate.query(query, resumeMapper);
    }

    public Optional<Long> create(Resume resume) {
        String query = "insert into RESUMES(USER_ID, NAME, CATEGORY_ID, SALARY, CREATED) values(?,?,?,?,?)";

        jdbcTemplate.update(connection -> {
            PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, resume.getUserId());
            pr.setString(2, resume.getName());
            pr.setObject(3, resume.getCategoryId());
            pr.setDouble(4, resume.getSalary());
            pr.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            return pr;
        }, keyHolder);

        Number number = keyHolder.getKey();
        return number != null ? Optional.of(number.longValue()) : Optional.empty();
    }

    public boolean updateResume(Resume resume) {
        String query = "UPDATE RESUMES " +
                "set NAME = ?, CATEGORY_ID = ?, SALARY = ?, IS_ACTIVE = ?" +
                "WHERE ID = ?";

        return jdbcTemplate.update(
                query,
                resume.getName(),
                resume.getCategoryId(),
                resume.getSalary(),
                resume.getIsActive() == null,
                resume.getId()
                ) > 0;
    }

    public Optional<Resume> findResumeById(Long resumeId) {
        String query = "select * from RESUMES where ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, resumeMapper, resumeId));
    }

    public boolean deleteResumeById(Long resumeId) {
        String query = "delete from RESUMES where ID = ?";

        return jdbcTemplate.update(query, resumeId) > 0;
    }

    public List<Resume> findResumeByUserId(Long userId) {
        String query = "select * from RESUMES where USER_ID = ?";

        return jdbcTemplate.query(query, resumeMapper, userId);
    }
}
