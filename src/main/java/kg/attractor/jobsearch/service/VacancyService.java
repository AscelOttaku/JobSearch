package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.List;

public interface VacancyService {
    VacancyDto findVacancyById(Long vacancyId);

    boolean isVacancyExist(Long vacancyId);

    VacancyDto createdVacancy(VacancyDto vacancyDto);

    VacancyDto updateVacancy(Long vacancyId, VacancyDto vacancyDto);

    boolean deleteVacancy(Long vacancyId);

    List<VacancyDto> findAllActiveVacancies();

    List<VacancyDto> findVacanciesByCategory(Long id);

    List<VacancyDto> findUserRespondedVacancies(String userEmail);

    List<VacancyDto> findAllVacancies();
}
