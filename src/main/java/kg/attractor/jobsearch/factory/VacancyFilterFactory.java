package kg.attractor.jobsearch.factory;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.FavoritesService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.BiFunction;

@Component
public class VacancyFilterFactory {
    private final VacancyRepository vacancyRepository;
    private final PageHolderWrapper wrapper;
    private final Map<FilterType, BiFunction<Integer, Integer, PageHolder<VacancyDto>>> filterStrategies = new EnumMap<>(FilterType.class);
    private final AuthorizedUserService authorizedUserService;
    private final FavoritesService favoritesService;

    public VacancyFilterFactory(VacancyRepository vacancyRepository, PageHolderWrapper wrapper, AuthorizedUserService authorizedUserService, FavoritesService favoritesService) {
        this.vacancyRepository = vacancyRepository;
        this.wrapper = wrapper;
        this.authorizedUserService = authorizedUserService;
        this.favoritesService = favoritesService;
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

        filterStrategies.put(FilterType.FAVORITES, this::findUserFavoriteVacancies);

        filterStrategies.put(FilterType.NEW, (page, size) -> wrapper.wrapVacancies(
                () -> vacancyRepository
                        .findAllActiveVacancies(
                                PageRequest.of(
                                        page, size, Sort.by("updated").descending())
                        ),
                FilterType.NEW
        ));
    }

    private PageHolder<VacancyDto> findUserFavoriteVacancies(int page, int size) {
        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        Page<Vacancy> vacancies;
        Pageable pageable = PageRequest.of(page, size);

        if (authUserDto.getAccountType().equals("EMPLOYER"))
            vacancies = vacancyRepository.findCompanyFavoriteVacancies(
                    authUserDto.getUserId(), pageable
            );
        else
            vacancies = vacancyRepository.findJobSeekerFavoriteVacancies(
                    authUserDto.getUserId(), pageable
            );

        PageHolder<VacancyDto> vacancyDtoPageHolder = wrapper.wrapVacancies(() -> vacancies, FilterType.FAVORITES);
        vacancyDtoPageHolder.getContent().forEach(vacancyDto -> vacancyDto.setFavoritesDtos(favoritesService.findALlUserFavorites()));
        return vacancyDtoPageHolder;
    }

    public PageHolder<VacancyDto> filter(int page, int size, FilterType filterType) {
        return filterStrategies.get(filterType).apply(page, size);
    }
}
