package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.RespondedApplication;
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
public class RespondApplicationDao {
    private final JdbcTemplate jdbcTemplate;
    private final BeanPropertyRowMapper<RespondedApplication> respondApplicationRowMapper = new BeanPropertyRowMapper<>(RespondedApplication.class);

    public Optional<Long> createRespond(RespondedApplication respondApplication) {
        String query = "insert into RESPONDED_APPLICATION(RESUME_ID, VACANCY_ID, CONFIRMATION) " +
                "VALUES ( ?,?,? )";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, respondApplication.getResumeId());
            preparedStatement.setLong(2, respondApplication.getVacancyId());
            preparedStatement.setBoolean(3, respondApplication.getConfirmation());
            return preparedStatement;
        }, keyHolder);

        Number number = keyHolder.getKey();
        return number != null ? Optional.of(number.longValue()) : Optional.empty();
    }

    public Optional<RespondedApplication> findRespondApplicationById(long id) {
        String query = "select * from RESPONDED_APPLICATION where ID = ?";

        return handleDataAccessException(() -> jdbcTemplate.queryForObject(query, respondApplicationRowMapper, id));
    }

    public List<RespondedApplication> findAll() {
        String query = "select * from RESPONDED_APPLICATION";

        return jdbcTemplate.query(query, respondApplicationRowMapper);
    }
}
