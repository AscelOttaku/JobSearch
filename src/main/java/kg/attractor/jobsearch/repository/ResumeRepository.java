package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findResumesByCategoryId(Long categoryId);

    @Query("select r from Resume r where r.user.userId = :userId order by coalesce(r.updated, r.created) desc")
    Page<Resume> findResumeByUserId(Long userId, Pageable pageable);

    @Query("select r from Resume r where r.user.userId = :userId")
    List<Resume> findResumeByUserId(Long userId);

    @Query("select r from Resume r order by coalesce(r.created, r.updated)")
    Page<Resume> findAllResumes(Pageable pageable);

    @Query("select r from Resume r where r.user.userId = :userId and r.isActive = true")
    Page<Resume> findActiveResumesByUserId(Long userId, Pageable pageable);

    @Query("select r from Resume r " +
            "join RespondedApplication ra on r.id = ra.resume.id " +
            "where ra.id = :respondedApplicationId and ra.vacancy.id = :vacancyId")
    List<Resume> findAlLResumesByRespondIdAndVacancyId(Long respondedApplicationId, Long vacancyId);

    @Query("select r from Resume r " +
            "left join RespondedApplication ra on ra.resume.id = r.id " +
            "where ra.vacancy.id = :vacancyId")
    Page<Resume> findRespondedToVacancyResumes(Long vacancyId, Pageable pageable);

    @Query("select r from Resume r " +
            "left join RespondedApplication ra on ra.resume.id = r.id " +
            "left join Vacancy v on v.id = ra.vacancy.id " +
            "where ra.vacancy.id in :vacancyIds")
    List<Resume> findAllRespondedResumesByVacancyIds(List<Long> vacancyIds);

    @Query("select r from Resume r " +
            "left join RespondedApplication ra on ra.resume.id = r.id " +
            "where ra.id = :respondId")
    Optional<Resume> findResumeByRespondId(Long respondId);
}
