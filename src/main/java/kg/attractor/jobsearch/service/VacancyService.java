package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.Optional;

public interface VacancyService {
    Optional<VacancyDto> findVacancyById(Long vacancyId);

    boolean createVacancy(VacancyDto vacancyDto);

    boolean updateVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Long vacancyId);
}
