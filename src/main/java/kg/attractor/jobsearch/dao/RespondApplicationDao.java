package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.RespondedApplication;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static kg.attractor.jobsearch.util.ExceptionHandler.handleDataAccessException;

@Component
@RequiredArgsConstructor
public class RespondApplicationDao {
    private final JdbcTemplate jdbcTemplate;

    public Optional<Boolean> createRespond(RespondedApplication respondApplication) {
        String query = "insert into RESPONDED_APPLICATION(RESUME_ID, VACANCY_ID, CONFIRMATION) " +
                "VALUES ( ?,?,? )";

        return handleDataAccessException(() -> jdbcTemplate.update(
                query, respondApplication.getResumeId(), respondApplication.getVacancyId(), respondApplication.getConfirmation()
        ) > 0);
    }
}
