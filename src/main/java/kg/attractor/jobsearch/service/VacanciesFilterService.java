package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.enums.FilterType;

public interface VacanciesFilterService {
    PageHolder<VacancyDto> filterVacanciesBy(FilterType filterType, int page, int size);

    PageHolder<VacancyDto> filterUserCreatedVacanciesBy(FilterType filterType, int page, int size);
}
