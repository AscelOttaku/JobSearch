package kg.attractor.jobsearch.strategy.vacancies;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.repository.VacancyRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

@Service
public class VacancyFilterStrategy {
    private final VacancyRepository vacancyRepository;
    private final PageHolderWrapper wrapper;
    private final Map<FilterType, BiFunction<Integer, Integer, PageHolder<VacancyDto>>> filterStrategies = new EnumMap<>(FilterType.class);

    public VacancyFilterStrategy(VacancyRepository vacancyRepository, PageHolderWrapper wrapper) {
        this.vacancyRepository = vacancyRepository;
        this.wrapper = wrapper;
        setFilterStrategies();
    }

    private void setFilterStrategies() {
        filterStrategies.put(FilterType.OLD, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository
                        .findAllActiveVacancies(
                                PageRequest.of(
                                        page, size, Sort.by("updated").ascending())
                        ),
                FilterType.OLD));

        filterStrategies.put(FilterType.SALARY_ASC, (page, size) -> wrapper.wrapVacancies(
                        () -> vacancyRepository
                                .findAllActiveVacancies(
                                        PageRequest.of(
                                                page, size, Sort.by("salary"))
                                ),
                        FilterType.SALARY_ASC)
        );

        filterStrategies.put(FilterType.SALARY_DESC, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository
                        .findAllActiveVacancies(
                                PageRequest.of(
                                        page, size, Sort.by("salary").descending())
                        ),
                FilterType.SALARY_DESC
        ));

        filterStrategies.put(FilterType.RESPONSES, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository.findActiveVacanciesOrderedByResponsesNumberDes(PageRequest.of(page, size)),
                FilterType.RESPONSES
        ));

        filterStrategies.put(FilterType.NEW, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository
                        .findAllActiveVacancies(
                                PageRequest.of(
                                        page, size, Sort.by("updated").descending())
                        ),
                FilterType.NEW
        ));
    }

    public void addFilterStrategy(FilterType filterType, BiFunction<Integer, Integer, PageHolder<VacancyDto>> filterStrategy) {
        Assert.notNull(filterType, "filterType must not be null");
        Assert.notNull(filterStrategy, "filterStrategy must not be null");

        filterStrategies.put(filterType, filterStrategy);
    }

    public PageHolder<VacancyDto> filter(int page, int size, FilterType filterType) {
        return filterStrategies.get(filterType).apply(page, size);
    }
}
