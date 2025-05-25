package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.JobSeekerFavorites;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobSeekerFavoritesRepository extends JpaRepository<JobSeekerFavorites, Long> {
    Optional<JobSeekerFavorites> findFavoriteByUserUserIdAndVacancyId(Long jobSeekerId, Long vacancyId);

    @Query("select f from JobSeekerFavorites f where f.user.userId = :userId")
    List<JobSeekerFavorites> findAllJobSeekerFavoritesByUserId(Long userId);
}
