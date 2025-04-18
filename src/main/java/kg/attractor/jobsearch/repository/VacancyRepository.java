package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("select v from Vacancy v where v.isActive = true")
    List<Vacancy> findIsActiveVacancies();

    List<Vacancy> findVacanciesByCategoryId(Long categoryId);

    List<Vacancy> findVacanciesByUserEmail(String email);

    @Query("select v from Vacancy v " +
            "join Role r on r.id = v.user.role.id " +
            "where v.user.userId = :userId and " +
            "r.role ilike 'EMPLOYER'")
    List<Vacancy> findUserVacanciesByUserId(Long userId);

    @Modifying
    @Query("update Vacancy v set v.updated = CURRENT_TIMESTAMP where v.id = :vacancyId")
    void updateVacancyTime(@Param("vacancyId") Long vacancyId);
}
