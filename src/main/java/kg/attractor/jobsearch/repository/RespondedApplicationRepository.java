package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.RespondedApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
            "                where u.userId = :userId And ra.confirmation = TRUE")
    List<RespondedApplication> findActiveRespondedApplicationsByUserId(Long userId);

    @Query("select ra from RespondedApplication ra " +
            "                JOIN Vacancy v ON v.id = ra.resume.id " +
            "                JOIN User u ON u.userId = v.user.userId " +
            "                where u.userId = :userId")
    List<RespondedApplication> findRespondedApplicationsByEmployerId(Long userId);

    @Query("select ra from RespondedApplication ra " +
            "join Resume r on r.id = ra.resume.id " +
            "join User u on u.userId = r.user.userId " +
            "where u.email ilike :email")
    Page<RespondedApplication> findAllRespondedApplicationsByUserEmail(String email, Pageable pageable);

    @Query("select ra from RespondedApplication ra " +
            "join Resume r on r.id = ra.resume.id " +
            "join User u on u.userId = r.user.userId " +
            "where u.email ilike :email")
    List<RespondedApplication> findAllRespondedApplicationsByUserEmail(String email);

    @Query("select ra from RespondedApplication ra " +
            "join Resume r on r.id = ra.resume.id " +
            "join User u on u.userId = r.user.userId " +
            "where u.userId = :userId")
    List<RespondedApplication> findAllRespondedApplicationsByJobSeekerId(Long userId);

    @Query("select ra from RespondedApplication ra " +
            "join Vacancy v on v.id = ra.vacancy.id " +
            "join User u on u.userId = v.user.userId " +
            "where u.email ilike :email")
    Page<RespondedApplication> findAllRespondedApplicationsByEmployerEmail(String email, Pageable pageable);

    Optional<RespondedApplication> findRespondedApplicationByVacancyIdAndResumeId(Long vacancyId, Long resumeId);

    List<RespondedApplication> findAllRespondedApplicationsByVacancyId(Long vacancyId);
}
