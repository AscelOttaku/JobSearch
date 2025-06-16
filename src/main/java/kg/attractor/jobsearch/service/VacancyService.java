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

    PageHolder<VacancyDto> filterVacancies(int page, int size, FilterType filterType);

    PageHolder<VacancyDto> filterVacanciesByCategoryName(String categoryNam, int page, int size);

    PageHolder<VacancyDto> filterUserVacancies(int page, int size, FilterType filterType);

    List<VacancyDto> searchVacancies(String query);

    VacancyDto findVacancyByRespondId(Long respondId);

    PageHolder<VacancyDto> findAllUserRespondedVacanciesByResumeId(Long resumeId, int page, int size);

    Long findAuthUserCreatedVacanciesQuantity();

    PageHolder<VacancyDto> findVacanciesBySearchCriteria(String searchCriteria, int page, int size);
}
