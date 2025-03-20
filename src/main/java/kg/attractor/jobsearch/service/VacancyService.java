package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface VacancyService {
    VacancyDto findVacancyById(Long vacancyId);

    Long createVacancy(VacancyDto vacancyDto);

    Long updateVacancy(VacancyDto vacancyDto);

    boolean deleteVacancy(Long vacancyId);

    List<VacancyDto> findAllActiveVacancies();

    List<VacancyDto> findVacanciesByCategory(Category category);

    Long createRespond(Long vacancyId, Long resumeId);

    List<VacancyDto> findUserRespondedVacancies(User user);

    List<VacancyDto> findAllVacancies();
}
