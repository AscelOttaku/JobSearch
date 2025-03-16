package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;
import java.util.Optional;

public interface VacancyService {
    Optional<VacancyDto> findVacancyById(Long vacancyId);

    boolean createVacancy(VacancyDto vacancyDto);

    boolean updateVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Long vacancyId);

    List<VacancyDto> findActiveVacancies();

    Optional<List<VacancyDto>> findVacanciesByCategory(String category);

    boolean createRespond(Long vacancyId, Long resumeId);
}
