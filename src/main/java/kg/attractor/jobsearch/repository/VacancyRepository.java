package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long>, JpaSpecificationExecutor<Vacancy> {

    @Query("select v from Vacancy v where v.isActive = true order by coalesce(v.updated, v.created) desc")
    Page<Vacancy> findIsActiveVacanciesSortedByDate(Pageable pageable);

    List<Vacancy> findVacanciesByCategoryId(Long categoryId);

    List<Vacancy> findVacanciesByUserEmail(String email);

    @Query("select v from Vacancy v " +
            "join Role r on r.id = v.user.role.id " +
            "where v.user.userId = :userId and " +
            "r.roleName ilike 'EMPLOYER'")
    Page<Vacancy> findUserVacanciesByUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Vacancy v set v.updated = CURRENT_TIMESTAMP where v.id = :vacancyId")
    void updateVacancyTime(@Param("vacancyId") Long vacancyId);

    @Query("select v from Vacancy v order by coalesce(v.updated, v.created) desc")
    Page<Vacancy> findAllVacancies(Pageable pageable);

    long count();

    List<Vacancy> findVacanciesByUserUserId(Long userId);

    @Query("select v from Vacancy v where v.isActive = true order by coalesce(v.updated, v.created) asc")
    Page<Vacancy> findIsActiveTrueOrderByDateAsc(Pageable pageable);

    @Query("select v from Vacancy v where v.isActive = true")
    Page<Vacancy> findAllActiveVacancies(Pageable pageable);

    @Query("select v from Vacancy v " +
            "where v.isActive = true " +
            "order by (select count(ra) from RespondedApplication ra " +
            "where ra.vacancy.id = v.id) desc")
    Page<Vacancy> findActiveVacanciesOrderedByResponsesNumberDes(Pageable pageable);

    @Query("select v from Vacancy v " +
            "left join User u on u.userId = v.user.userId " +
            "left join Role r on r.id = v.user.role.id " +
            "where v.user.userId = :userId and r.roleName " +
            "like 'EMPLOYER'" +
            " order by " +
            "(select count(ra) from RespondedApplication ra " +
            "where ra.vacancy.id = v.id) desc")
    Page<Vacancy> findUserVacanciesOrderedByResponsesNumberDesc(Long userId, Pageable pageable);

    @Query("select v from Vacancy v " +
            "left join CompanyFavorites f on f.vacancy.id = v.id " +
            "where f.user.userId = :userId")
    Page<Vacancy> findCompanyFavoriteVacancies(Long userId, Pageable pageable);

    @Query("select v from Vacancy v " +
            "left join JobSeekerFavorites f on f.vacancy.id = v.id " +
            "where f.user.userId = :userId")
    Page<Vacancy> findJobSeekerFavoriteVacancies(Long userId, Pageable pageable);

    @Query("select v from Vacancy v where lower(v.name) like lower(concat('%', :vacancyName, '%'))")
    List<Vacancy> searchVacanciesByName(String vacancyName);

    @Query("select v from Vacancy v " +
            "left join RespondedApplication ra on ra.vacancy.id = v.id " +
            "where ra.id = :respondId")
    Optional<Vacancy> findVacancyByRespondedApplicationId(Long respondId);

    Page<Vacancy> findVacancyByCategoryName(String categoryName, Pageable pageable);

    @Query("select v from Vacancy v " +
            "left join RespondedApplication ra on ra.vacancy.id = v.id " +
            "where ra.resume.id = :resumeId and v.user.userId = :userId")
    Page<Vacancy> findUserRespondedVacancies(Long resumeId, Long userId, Pageable pageable);

    @Query(value = "select result.quantity " +
            "from (select v.VACANCY_USER_ID, count(v.id) as quantity " +
            "      from VACANCIES v " +
            "where v.VACANCY_USER_ID = :userId" +
            "      group by v.VACANCY_USER_ID) " +
            "         as result", nativeQuery = true)
    Optional<Long> findUserCreatedVacanciesQuantity(Long userId);

    @Query("select v from Vacancy v " +
            "where v.user.userId = :userId " +
            "order by v.id desc limit 1")
    Optional<Vacancy> findUserCreatedLastVacancy(Long userId);
}
