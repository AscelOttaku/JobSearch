package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    Optional<VacancyDto> findVacancyById(Long vacancyId);

    Long createVacancy(VacancyDto vacancyDto);

    Long updateVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Long vacancyId);

    List<VacancyDto> findActiveVacancies();

    Optional<List<VacancyDto>> findVacanciesByCategory(String category);

    Long createRespond(Long vacancyId, Long resumeId);
}
