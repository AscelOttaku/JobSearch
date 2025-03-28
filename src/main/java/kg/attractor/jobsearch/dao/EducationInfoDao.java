package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.EducationInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class EducationInfoDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<EducationInfo> educationInfoRowMapper = new BeanPropertyRowMapper<>(EducationInfo.class);
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public Optional<Long> create(EducationInfo educationInfo) {
        String query = "insert into EDUCATION_INFO(resume_id, institution, program, start_date, end_date, degree)" +
                "values ( ?,?,?,?,?,? )";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, educationInfo.getResumeId());
            ps.setString(2, educationInfo.getInstitution());
            ps.setString(3, educationInfo.getProgram());
            ps.setTimestamp(4, getTimeStampOrNull(educationInfo.getStartDate()));
            ps.setTimestamp(5, getTimeStampOrNull(educationInfo.getEndDate()));
            ps.setString(6, educationInfo.getDegree());
            return ps;

        }, keyHolder);

        return getGeneratedKey(keyHolder);
    }

    private static Timestamp getTimeStampOrNull(LocalDateTime educationInfo) {
        return educationInfo != null ? Timestamp.valueOf(educationInfo) :
                null;
    }

    private Optional<Long> getGeneratedKey(KeyHolder keyHolder) {
        try {
            Number number = keyHolder.getKey();
            return number != null ? Optional.of(number.longValue()) : Optional.empty();
        } catch (InvalidDataAccessApiUsageException e) {
            logger.warn(e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<EducationInfo> findEducationInfoById(Long id) {
        String query = "select * from EDUCATION_INFO where id = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, educationInfoRowMapper, id));
    }

    public Optional<EducationInfo> findEducationalInfoByResumeId(Long resumeId) {
        String query = "select * from EDUCATION_INFO where resume_id = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, educationInfoRowMapper, resumeId));
    }

    public boolean isEducationInfoExist(long id) {
        return findEducationalInfoByResumeId(id).isPresent();
    }

    public void updateEducationInfo(EducationInfo educationInfo) {
        String query = "update EDUCATION_INFO " +
                "set institution = ?, program = ?, START_DATE = ?, END_DATE = ?, DEGREE = ? " +
                "where ID = ?";

        jdbcTemplate.update(
                query, educationInfo.getInstitution(),
                educationInfo.getProgram(),
                educationInfo.getStartDate(),
                educationInfo.getEndDate(),
                educationInfo.getDegree(),
                educationInfo.getId()
        );
    }

    public List<EducationInfo> findAllEducationInfos() {
        String query = "select * from EDUCATION_INFO";

        return jdbcTemplate.query(query, educationInfoRowMapper);
    }
}
