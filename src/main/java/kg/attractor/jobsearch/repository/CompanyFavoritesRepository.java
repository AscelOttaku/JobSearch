package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.CompanyFavorites;
import kg.attractor.jobsearch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyFavoritesRepository extends JpaRepository<CompanyFavorites, Long> {

    @Query("select f from CompanyFavorites f " +
            "where f.user.userId = :companyId " +
            "and (f.vacancy.id = :vacancyId or :vacancyId is null) " +
            "and (f.resume.id = :resumeId or :resumeId is null)")
    Optional<CompanyFavorites> findFavoritesByUserIdAndVacancyIdAndResumeId(
            Long companyId, Long vacancyId, Long resumeId
    );

    @Query("select f from CompanyFavorites f where f.user.userId = :userId")
    List<CompanyFavorites> findAllCompaniesFavoritesByUserId(Long userId);

    Long resume(Resume resume);
}
