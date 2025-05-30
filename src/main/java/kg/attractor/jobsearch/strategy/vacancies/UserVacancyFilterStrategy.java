package kg.attractor.jobsearch.strategy.vacancies;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class UserVacancyFilterStrategy {
    private final VacancyRepository vacancyRepository;
    private final PageHolderWrapper wrapper;
    private final AuthorizedUserService authorizedUserService;

    private final Map<FilterType, BiFunction<Integer, Integer, PageHolder<VacancyDto>>> strategies = new EnumMap<>(FilterType.class);

    public UserVacancyFilterStrategy(VacancyRepository vacancyRepository,
                                     PageHolderWrapper wrapper,
                                     AuthorizedUserService authorizedUserService) {
        this.vacancyRepository = vacancyRepository;
        this.wrapper = wrapper;
        this.authorizedUserService = authorizedUserService;
    }

    public void initStrategies() {
        Long userId = authorizedUserService.getAuthorizedUserId();

        strategies.put(FilterType.OLD, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository.findUserVacanciesByUserId(userId,
                        PageRequest.of(page, size, Sort.by("updated").ascending())),
                FilterType.OLD
        ));

        strategies.put(FilterType.SALARY_ASC, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository.findUserVacanciesByUserId(userId,
                        PageRequest.of(page, size, Sort.by("salary").ascending())),
                FilterType.SALARY_ASC
        ));

        strategies.put(FilterType.SALARY_DESC, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository.findUserVacanciesByUserId(userId,
                        PageRequest.of(page, size, Sort.by("salary").descending())),
                FilterType.SALARY_DESC
        ));

        strategies.put(FilterType.RESPONSES, this::findUserVacanciesOrderedByResponsesNumbersDesc);

        strategies.put(FilterType.NEW, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository.findUserVacanciesByUserId(userId,
                        PageRequest.of(page, size, Sort.by("updated").descending())),
                FilterType.NEW
        ));
    }

    public void addFilterStrategy(FilterType filterType, BiFunction<Integer, Integer, PageHolder<VacancyDto>> function) {
        Assert.notNull(filterType, "filterType must not be null");
        Assert.notNull(function, "function must not be null");

        strategies.put(filterType, function);
    }

    private PageHolder<VacancyDto> findUserVacanciesOrderedByResponsesNumbersDesc(int page, int size) {
        Long userId = authorizedUserService.getAuthorizedUserId();
        Page<Vacancy> vacancies = vacancyRepository.findUserVacanciesOrderedByResponsesNumberDesc(
                userId,
                PageRequest.of(page, size)
        );
        return wrapper.wrapVacancies(() -> vacancies, FilterType.RESPONSES);
    }

    public PageHolder<VacancyDto> filter(int page, int size, FilterType filterType) {
        return strategies.getOrDefault(filterType, strategies.get(FilterType.NEW)).apply(page, size);
    }
}
