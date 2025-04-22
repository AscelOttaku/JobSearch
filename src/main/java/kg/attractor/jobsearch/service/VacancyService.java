package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.enums.FilterType;

import java.util.List;

public interface VacancyService {
    VacancyDto findVacancyById(Long vacancyId);

    boolean isVacancyExist(Long vacancyId);

    VacancyDto createdVacancy(VacancyDto vacancyDto);

    VacancyDto updateVacancy(VacancyDto vacancyDto);

    void deleteVacancy(Long vacancyId);

    PageHolder<VacancyDto> findAllActiveVacancies(int page, int size);

    List<VacancyDto> findVacanciesByCategory(Long id);

    List<VacancyDto> findUserRespondedVacancies();

    PageHolder<VacancyDto> findAllVacancies(int page, int pageSize);

    Long findVacancyOwnerByVacancyId(Long vacancyId);

    PageHolder<VacancyDto> findUserCreatedVacancies(int page, int size);

    VacancyDto findAuthorizedUsersVacancyById(Long vacancyId);

    void updateVacancyDate(Long vacancyId);

    Long findVacanciesQuantity(Long employerId);

    PageHolder<VacancyDto> findVacanciesByUserId(Long userId, int page, int size);

    List<VacancyDto> findVacanciesByUserId(Long userId);

    PageHolder<VacancyDto> filterVacanciesBy(FilterType filterType, int page, int size);
}
