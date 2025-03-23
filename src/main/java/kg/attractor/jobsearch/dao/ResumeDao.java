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
        String query = "insert into RESUMES(USER_ID, NAME, CATEGORY_ID, SALARY, IS_ACTIVE) values(?,?,?,?,?)";

        executeCreateQuery(resume, query);

        Number number = keyHolder.getKey();
        return number != null ? Optional.of(number.longValue()) : Optional.empty();
    }

    public Optional<Long> updateResume(Resume resume) {
        String query = "UPDATE RESUMES " +
                "SET ID = ?, USER_ID = ?, NAME = ?, CATEGORY_ID = ?, SALARY = ?, IS_ACTIVE = ?" +
                "WHERE ID = ?";

        executeUpdateQuery(resume, query);

        Number number = keyHolder.getKey();
        return number != null ? Optional.of(number.longValue()) : Optional.empty();
    }

    public Optional<Resume> findResumeById(Long resumeId) {
        String query = "select * from RESUMES where ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, resumeMapper, resumeId));
    }

    public boolean isResumeExistById(Long resumeId) {
        return findResumeById(resumeId).isPresent();
    }

    public boolean deleteResumeById(Long resumeId) {
        String query = "delete from RESUMES where ID = ?";

        return jdbcTemplate.update(query, resumeId) > 0;
    }

    public void executeCreateQuery(
            Resume resume, String query
    ) {
        jdbcTemplate.update(connection -> {
            PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, resume.getUserId());
            pr.setString(2, resume.getName());
            pr.setObject(3, resume.getCategoryId());
            pr.setDouble(4, resume.getSalary());
            pr.setBoolean(5, resume.getIsActive());

            return pr;
        }, keyHolder);
    }

    private void executeUpdateQuery(
            Resume resume, String query
    ) {
        jdbcTemplate.update(connection -> {
            PreparedStatement pr = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pr.setLong(1, resume.getId());
            pr.setLong(2, resume.getUserId());
            pr.setString(3, resume.getName());
            pr.setObject(4, resume.getCategoryId());
            pr.setDouble(5, resume.getSalary());
            pr.setBoolean(6, resume.getIsActive());

            pr.setLong(7, resume.getId());

            return pr;
        }, keyHolder);
    }
}
