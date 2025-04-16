package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("select v from Vacancy v where v.isActive = true")
    List<Vacancy> findIsActiveVacancies();

    List<Vacancy> findVacanciesByCategoryId(Long categoryId);

    List<Vacancy> findVacanciesByUserEmail(String email);

    @Query("select v from Vacancy v where v.user.userId = :userId and " +
            "v.user.accountType ilike 'EMPLOYER'")
    List<Vacancy> findUserVacanciesByUserId(Long userId);
}
