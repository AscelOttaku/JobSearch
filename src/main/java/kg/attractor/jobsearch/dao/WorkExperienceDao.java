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
            ps.setInt(2, workExperienceInfo.getYears());
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
}
