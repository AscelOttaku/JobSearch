package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    List<Resume> findResumesByCategoryId(Long categoryId);

    @Query("select r from Resume r where r.user.userId = :userId")
    Page<Resume> findResumeByUserId(Long userId, Pageable pageable);

    @Query("select r from Resume r where r.user.userId = :userId")
    List<Resume> findResumeByUserId(Long userId);
}
