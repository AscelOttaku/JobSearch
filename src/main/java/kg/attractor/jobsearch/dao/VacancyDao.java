package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;
import java.util.Optional;

public interface VacancyDao {
    List<Vacancy> findVacanciesByUserid(Long userId);

    List<Vacancy> findAllVacancies();

    List<Vacancy> findVacancyByCategory(Long categoryId);

    Optional<Vacancy> findVacancyById(Long vacancyId);

    boolean deleteVacancyById(Long vacancyId);

    List<Vacancy> findAllActiveVacancies();
}
