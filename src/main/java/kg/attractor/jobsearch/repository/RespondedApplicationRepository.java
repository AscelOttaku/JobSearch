package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespondedApplicationRepository extends JpaRepository<RespondedApplication, Long> {

    @Query("select ra from RespondedApplication ra " +
            "                JOIN Resume r ON r.id = ra.resume.id " +
            "                JOIN User u ON u.userId = r.user.userId " +
            "                where u.userId = :resumeId And ra.confirmation = TRUE")
    List<RespondedApplication> findActiveRespondedApplicationsByUserId(Long resumeId);

    Optional<RespondedApplication> findRespondedApplicationByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    Long vacancy(Vacancy vacancy);
}
