package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.VacanciesFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacanciesFilterServiceImpl implements VacanciesFilterService {
    private final VacancyRepository vacancyRepository;
    private final PageHolderWrapper pageHolderWrapper;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public PageHolder<VacancyDto> filterVacanciesBy(FilterType filterType, int page, int size) {
        return switch (filterType) {
            case OLD -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                            .findAllActiveVacancies(
                                    PageRequest.of(
                                            page, size, Sort.by("updated").ascending())
                            ),
                    page, FilterType.OLD
            );
            case SALARY_ASC -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                            .findAllActiveVacancies(
                                    PageRequest.of(
                                            page, size, Sort.by("salary"))
                            ),
                    page, FilterType.SALARY_ASC
            );
            case SALARY_DESC -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                            .findAllActiveVacancies(
                                    PageRequest.of(
                                            page, size, Sort.by("salary").descending())
                            ),
                    page, FilterType.SALARY_ASC
            );
            case RESPONSES -> findActiveVacanciesOrderedByResponsesNumbersDesc(page, size);
            default -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                            .findAllActiveVacancies(
                                    PageRequest.of(
                                            page, size, Sort.by("updated").descending())
                            ),
                    page, FilterType.NEW
            );
        };
    }

    private PageHolder<VacancyDto> findActiveVacanciesOrderedByResponsesNumbersDesc(int page, int size) {
        Page<Vacancy> vacanciesFilteredByResponses = vacancyRepository.findActiveVacanciesOrderedByResponsesNumberDes(PageRequest.of(page, size));

        return pageHolderWrapper.wrapPageHolder(vacanciesFilteredByResponses, page, FilterType.RESPONSES);
    }

    private PageHolder<VacancyDto> findUserVacanciesOrderedByResponsesNumbersDesc(int page, int size) {
        Page<Vacancy> vacanciesFilteredByResponses = vacancyRepository.findUserVacanciesOrderedByResponsesNumberDesc(
                authorizedUserService.getAuthorizedUserId(),
                PageRequest.of(page, size)
        );

        return pageHolderWrapper.wrapPageHolder(vacanciesFilteredByResponses, page, FilterType.RESPONSES);
    }

    @Override
    public PageHolder<VacancyDto> filterUserCreatedVacanciesBy(FilterType filterType, int page, int size) {
        Long authorizedUserId = authorizedUserService.getAuthorizedUserId();

        return switch (filterType) {
            case OLD -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                    .findUserVacanciesByUserId(
                            authorizedUserId,
                            PageRequest.of(
                                    page, size, Sort.by("updated").ascending())
                    ), page, FilterType.OLD
            );

            case SALARY_ASC -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                    .findUserVacanciesByUserId(
                            authorizedUserId,
                            PageRequest.of(
                                    page, size, Sort.by("salary").ascending()
                            )), page, FilterType.SALARY_ASC
                    );

            case SALARY_DESC -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                    .findUserVacanciesByUserId(
                            authorizedUserId,
                            PageRequest.of(
                                    page, size, Sort.by("salary").descending()
                            )), page, FilterType.SALARY_DESC
                    );

            case RESPONSES -> findUserVacanciesOrderedByResponsesNumbersDesc(page, size);

            default -> pageHolderWrapper.wrapPageHolder(vacancyRepository
                    .findUserVacanciesByUserId(
                            authorizedUserId,
                            PageRequest.of(
                                    page, size, Sort.by("updated").descending()
                            )
                    ), page, FilterType.NEW
            );
        };
    }
}
