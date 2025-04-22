package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("select v from Vacancy v where v.isActive = true")
    Page<Vacancy> findIsActiveVacancies(Pageable pageable);

    List<Vacancy> findVacanciesByCategoryId(Long categoryId);

    List<Vacancy> findVacanciesByUserEmail(String email);

    @Query("select v from Vacancy v " +
            "join Role r on r.id = v.user.role.id " +
            "where v.user.userId = :userId and " +
            "r.roleName ilike 'EMPLOYER' " +
            "order by coalesce(v.updated, v.created) desc")
    Page<Vacancy> findUserVacanciesByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Vacancy v set v.updated = CURRENT_TIMESTAMP where v.id = :vacancyId")
    void updateVacancyTime(@Param("vacancyId") Long vacancyId);

    @Query("select v from Vacancy v order by coalesce(v.updated, v.created) desc")
    Page<Vacancy> findAllVacancies(Pageable pageable);

    long count();

    List<Vacancy> findVacanciesByUserUserId(Long userId);

    @Query("select v from Vacancy v where v.isActive = true order by v.salary desc")
    Page<Vacancy> findIsActiveTrueOrderBySalaryDesc(Pageable pageable);

    @Query("select v from Vacancy v where v.isActive = true order by v.salary")
    Page<Vacancy> findIsActiveTrueOrderBySalaryAsc(Pageable pageable);

    @Query("select v from Vacancy v where v.isActive = true order by coalesce(v.updated, v.created) asc")
    Page<Vacancy> findIsActiveTrueOrderByDateAsc(Pageable pageable);

    @Query("select v from Vacancy v " +
            "inner join RespondedApplication ra on ra.vacancy.id = v.id " +
            "where v.isActive = true " +
            "order by (select count(ra) from RespondedApplication ra " +
            "where ra.vacancy.id = v.id) desc")
    Page<Vacancy> findIsActiveTrueOrderedByResponsesNumberDesc(Pageable pageable);
}
