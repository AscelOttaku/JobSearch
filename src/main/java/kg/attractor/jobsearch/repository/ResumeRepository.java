package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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

    @Query("select r.name from Resume r " +
            "left join RespondedApplication ra on ra.resume.id = r.id " +
            "where ra.id = :respondId")
    Optional<String> findResumeNameByRespondId(Long respondId);

    Page<Resume> findAllResumesByCategoryName(String categoryName, Pageable pageable);


    @Query(value = "select result.quantity from " +
            "(select r.USER_ID, count(r.ID) as quantity from RESUMES r " +
            "where r.USER_ID = :userId " +
            "group by r.USER_ID) as result", nativeQuery = true)
    Optional<Long> findUserCreatedResumesQuantity(Long userId);

    Optional<Resume> findResumeById(Long id);

    @Query(value = "select r.* from RESUMES r " +
            "left join RESPONDED_APPLICATION ra on ra.RESUME_ID = r.id " +
            "where r.USER_ID = :userId " +
            "order by ra.id desc limit 1", nativeQuery = true)
    Optional<Resume> findUserUsedLastResume(Long userId);
}
