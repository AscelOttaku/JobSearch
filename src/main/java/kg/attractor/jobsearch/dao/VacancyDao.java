package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Vacancy;

import java.util.List;

public interface VacancyDao {
    List<Vacancy> findVacanciesByUserid(Long userId);

    List<Vacancy> findAllVacancies();

    List<Vacancy> findVacancyByCategory(Long categoryId);
}
