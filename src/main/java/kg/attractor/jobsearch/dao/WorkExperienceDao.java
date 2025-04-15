package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.InvalidDataAccessApiUsageException;
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
public class WorkExperienceDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<WorkExperienceInfo> workExperienceInfoBeanPropertyRowMapper = new BeanPropertyRowMapper<>(WorkExperienceInfo.class);

    public Optional<Long> create(WorkExperienceInfo workExperienceInfo) {
        String query = "insert into WORK_EXPERIENCE_INFO(resume_id, years, company_name, position, responsibilities) " +
                "values(?,?,?,?,?) ";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, workExperienceInfo.getResumeId());
            ps.setObject(2, workExperienceInfo.getYears());
            ps.setString(3, workExperienceInfo.getCompanyName());
            ps.setString(4, workExperienceInfo.getPosition());
            ps.setString(5, workExperienceInfo.getResponsibilities());
            return ps;
        }, keyHolder);

        return getGeneratedKey(keyHolder);
    }

    public Optional<Long> getGeneratedKey(KeyHolder keyHolder) {
        try {
            Number number = keyHolder.getKey();
            return number != null ? Optional.of(number.longValue()) : Optional.empty();
        } catch (InvalidDataAccessApiUsageException e) {
            return Optional.empty();
        }
    }

    public Optional<WorkExperienceInfo> findWorkExperienceById(long id) {
        String query = "select * from WORK_EXPERIENCE_INFO where id = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, workExperienceInfoBeanPropertyRowMapper, id));
    }

    public List<WorkExperienceInfo> findWorkExperienceByResumeId(long resumeId) {
        String query = "select * from WORK_EXPERIENCE_INFO where resume_id = ?";

        return jdbcTemplate.query(query, workExperienceInfoBeanPropertyRowMapper, resumeId);
    }

    public void updateWorkExperience(WorkExperienceInfo workExperienceInfo) {
        String query = "update WORK_EXPERIENCE_INFO " +
                "set RESUME_ID = ?, YEARS = ?, COMPANY_NAME = ?, POSITION = ?, RESPONSIBILITIES = ? " +
                "where ID = ?";

        jdbcTemplate.update(
                query, workExperienceInfo.getResumeId(),
                workExperienceInfo.getYears(),
                workExperienceInfo.getCompanyName(),
                workExperienceInfo.getPosition(),
                workExperienceInfo.getResponsibilities(),
                workExperienceInfo.getResumeId()
        );
    }

    public List<WorkExperienceInfo> findAllWorkExperienceInfos() {
        String query = "select * from WORK_EXPERIENCE_INFO";

        return jdbcTemplate.query(query, workExperienceInfoBeanPropertyRowMapper);
    }

    public List<WorkExperienceInfo> findWorkExperiencesInfoByResumeId(long resumeId) {
        String query = "select * from WORK_EXPERIENCE_INFO where resume_id = ?";

        return jdbcTemplate.query(query, workExperienceInfoBeanPropertyRowMapper, resumeId);
    }

    public void deleteWorkExperienceId(Long id) {
        String query = "delete from EDUCATION_INFO where ID = ?";

        jdbcTemplate.update(query, id);
    }
}
