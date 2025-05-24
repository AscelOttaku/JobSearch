package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.JobSeekerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JobSeekerFavoritesRepository extends JpaRepository<JobSeekerFavorites, Long> {
    Optional<JobSeekerFavorites> findFavoriteByUserUserIdAndVacancyId(Long jobSeekerId, Long vacancyId);
}
