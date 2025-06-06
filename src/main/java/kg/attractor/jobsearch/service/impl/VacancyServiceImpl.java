package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.SkillDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.enums.FilterType;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.provider.UserLastCreatedVacancyProvider;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.strategy.vacancies.UserVacancyFilterStrategy;
import kg.attractor.jobsearch.strategy.vacancies.VacancyFilterStrategy;
import kg.attractor.jobsearch.validators.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService, UserLastCreatedVacancyProvider {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final CategoryService categoryService;
    private final AuthorizedUserService authorizedUserService;
    private final PageHolderWrapper pageHolderWrapper;
    private final SkillService skillService;
    private final VacancyFilterStrategy vacancyFilterStrategy;
    private final UserVacancyFilterStrategy userVacancyFilterStrategy;
    private final SkillCorrespondenceService skillCorrespondenceService;

    @Override
    public VacancyDto findVacancyById(Long vacancyId) {
        ValidatorUtil.isValidId(vacancyId);

        return vacancyRepository.findById(vacancyId)
                .map(vacancyMapper::mapToDto)
                .orElseThrow(() -> new VacancyNotFoundException(
                        "vacancy by id " + vacancyId + " not found",
                        CustomBindingResult.builder()
                                .className(Vacancy.class.getSimpleName())
                                .fieldName("vacancyId")
                                .rejectedValue(vacancyId)
                                .build()
                ));
    }

    @Override
    public boolean isVacancyExist(Long vacancyId) {
        ValidatorUtil.isValidId(vacancyId);

        return vacancyRepository.findById(vacancyId).isPresent();
    }

    @Transactional
    @Override
    public VacancyDto createdVacancy(VacancyDto vacancyDto) {
        UserDto authorizedUser = new UserDto();
        authorizedUser.setUserId(authorizedUserService.getAuthorizedUserId());

        vacancyDto.setUser(authorizedUser);
        log.info("Create vacancy / user name: {}", authorizedUser.getName());

        List<SkillDto> skillDtos = skillService.saveNewSkills(vacancyDto.getSkills());
        vacancyDto.setSkills(skillDtos);
        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);

        return vacancyMapper.mapToDto(vacancyRepository.save(vacancy));
    }

    @Transactional
    @Override
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        ValidatorUtil.isValidId(vacancyDto.getVacancyId());

        UserDto user = new UserDto();
        user.setUserId(authorizedUserService.getAuthorizedUserId());

        vacancyDto.setUser(user);
        log.info("Updated vacancy / user name: {}", user.getName());

        List<SkillDto> skillDtos = skillService.saveNewSkills(vacancyDto.getSkills());
        vacancyDto.setSkills(skillDtos);
        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);

        return vacancyMapper.mapToDto(vacancyRepository.save(vacancy));
    }

    @Override
    public void deleteVacancy(Long vacancyId) {
        ValidatorUtil.isValidId(vacancyId);

        vacancyRepository.deleteById(vacancyId);
    }

    @Override
    public PageHolder<VacancyDto> findAllActiveVacancies(int page, int size) {
        Page<Vacancy> isActiveVacancies = vacancyRepository.findIsActiveVacanciesSortedByDate(PageRequest.of(page, size));

        PageHolder<VacancyDto> vacancyDtoPageHolder = pageHolderWrapper.wrapVacancies(() -> isActiveVacancies, FilterType.NEW);
        vacancyDtoPageHolder.getContent().forEach(vacancyDto -> vacancyDto.setSkillCorrespondence(skillCorrespondenceService.calculateAccordingToSKillsUsersCorrespondenceToVacancy(vacancyDto.getSkills())));
        log.warn("FilterType String value: {}", vacancyDtoPageHolder.getFilterType().name());
        return vacancyDtoPageHolder;
    }

    @Override
    public List<VacancyDto> findVacanciesByCategory(Long id) {
        ValidatorUtil.isValidId(id);

        boolean isCategoryExists = categoryService.checkIfCategoryExistsById(id);

        if (!isCategoryExists)
            throw new EntityNotFoundException(
                    "Category doesn't exists",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("categoryId")
                            .rejectedValue(id)
                            .build()
            );

        return vacancyRepository.findVacanciesByCategoryId(id)
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findUserRespondedVacancies() {
        UserDto user = authorizedUserService.getAuthorizedUser();

        boolean isUserJobSeeker = authorizedUserService.checkIfJobSeekerExistByEmail(user.getEmail());

        if (!isUserJobSeeker)
            throw new EntityNotFoundException(
                    "User account type is not job seeker",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("email")
                            .rejectedValue(user.getEmail())
                            .build()
            );

        return vacancyRepository.findVacanciesByUserEmail(user.getEmail())
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public PageHolder<VacancyDto> findAllVacancies(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page, pageSize);

        Page<Vacancy> vacanciesPage = vacancyRepository.findAllVacancies(pageable);
        PageHolder<VacancyDto> vacancyDtoPageHolder = pageHolderWrapper.wrapVacancies(() -> vacanciesPage, FilterType.NEW);
        vacancyDtoPageHolder.getContent().forEach(vacancyDto -> vacancyDto.setSkillCorrespondence(skillCorrespondenceService.calculateAccordingToSKillsUsersCorrespondenceToVacancy(vacancyDto.getSkills())));
        return vacancyDtoPageHolder;
    }

    @Override
    public Long findVacancyOwnerByVacancyId(Long vacancyId) {
        Optional<Vacancy> vacancy = vacancyRepository.findById(vacancyId);

        return vacancy
                .map(v -> v.getUser().getUserId())
                .orElse(-1L);
    }

    @Override
    public PageHolder<VacancyDto> findUserCreatedVacancies(int page, int size) {
        Long userId = authorizedUserService.getAuthorizedUserId();

        Pageable pageable = PageRequest.of(page, size, Sort.by("updated").descending());

        Page<Vacancy> vacanciesPage = vacancyRepository.findUserVacanciesByUserId(userId, pageable);

        return pageHolderWrapper.wrapVacancies(() -> vacanciesPage, FilterType.NEW);
    }

    @Override
    public VacancyDto findAuthorizedUsersVacancyById(Long vacancyId) {
        Long authorizedUserId = authorizedUserService.getAuthorizedUserId();
        VacancyDto vacancyDto = findVacancyById(vacancyId);

        if (!Objects.equals(vacancyDto.getUser().getUserId(), authorizedUserId)) {
            throw new EntityNotFoundException(
                    "User created vacancy by " + vacancyId + " not found",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("vacancyId")
                            .rejectedValue(vacancyId)
                            .build()
            );
        }

        return vacancyDto;
    }

    @Transactional
    @Override
    public void updateVacancyDate(Long vacancyId) {
        UserDto authorizedUser = authorizedUserService.getAuthorizedUser();

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new IllegalArgumentException("Vacancy not found by " + vacancyId));

        if (!Objects.equals(authorizedUser.getUserId(), vacancy.getUser().getUserId()))
            throw new IllegalArgumentException("user doesn't belongs this vacancy");

        vacancyRepository.updateVacancyTime(vacancyId);
    }

    @Override
    public Long findVacanciesQuantity(Long employerId) {
        return vacancyRepository.count();
    }

    @Override
    public PageHolder<VacancyDto> findVacanciesByUserId(Long userId, int page, int size) {
        Page<Vacancy> vacanciesPageHolder = vacancyRepository.findUserVacanciesByUserId(userId, PageRequest.of(page, size));

        return pageHolderWrapper.wrapVacancies(() -> vacanciesPageHolder, FilterType.NEW);
    }

    @Override
    public List<VacancyDto> findVacanciesByUserId(Long userId) {
        return vacancyRepository.findVacanciesByUserUserId(userId)
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public PageHolder<VacancyDto> filterVacancies(int page, int size, FilterType filterType) {
        Assert.notNull(filterType, "filterType must not be null");

        vacancyFilterStrategy.addFilterStrategy(FilterType.FAVORITE_VACANCIES, this::findUserFavoriteVacancies);
        PageHolder<VacancyDto> filteredVacancies = vacancyFilterStrategy.filter(page, size, filterType);
        filteredVacancies.getContent().forEach(vacancyDto -> vacancyDto.setSkillCorrespondence(skillCorrespondenceService.calculateAccordingToSKillsUsersCorrespondenceToVacancy(vacancyDto.getSkills())));
        return filteredVacancies;
    }

    @Override
    public PageHolder<VacancyDto> filterVacanciesByCategoryName(String categoryNam, int page, int size) {
        Assert.notNull(categoryNam, "Category name cannot be empty");

        Page<VacancyDto> vacancyByCategoryName = vacancyRepository.findVacancyByCategoryName(categoryNam, PageRequest.of(page, size))
                .map(vacancyMapper::mapToDto);

        PageHolder<VacancyDto> vacancyDtoPageHolder = pageHolderWrapper.wrapPageHolder(vacancyByCategoryName);
        vacancyDtoPageHolder.getContent().forEach(vacancyDto -> vacancyDto.setSkillCorrespondence(skillCorrespondenceService.calculateAccordingToSKillsResumeCorrespondenceToVacancy(vacancyDto.getSkills())));
        return vacancyDtoPageHolder;
    }

    @Override
    public PageHolder<VacancyDto> filterUserVacancies(int page, int size, FilterType filterType) {
        Assert.notNull(filterType, "filter type must not be null");

        userVacancyFilterStrategy.initStrategies();
        userVacancyFilterStrategy.addFilterStrategy(FilterType.FAVORITE_VACANCIES, this::findUserFavoriteVacancies);
        return userVacancyFilterStrategy.filter(page, size, filterType);
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

        return pageHolderWrapper.wrapVacancies(() -> vacancies, FilterType.FAVORITE_VACANCIES);
    }

    @Override
    public List<VacancyDto> searchVacancies(String query) {
        Assert.notNull(query, "query cannot be null");

        return vacancyRepository.searchVacanciesByName(query)
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public VacancyDto findVacancyByRespondId(Long respondId) {
        Assert.notNull(respondId, "Respond id cannot be null");

        return vacancyRepository.findVacancyByRespondedApplicationId(respondId)
                .map(vacancyMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("vacancy not found by " + respondId));
    }

    @Override
    public PageHolder<VacancyDto> findAllUserRespondedVacanciesByResumeId(Long resumeId, int page, int size) {
        Assert.notNull(resumeId, "Resume id cannot be null");

        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        Assert.isTrue(authUserDto.getAccountType().equals("EMPLOYER"), "user account type should be equals employer");

        Page<VacancyDto> userRespondedVacancies = vacancyRepository.findUserRespondedVacancies(resumeId, authUserDto.getUserId(), PageRequest.of(page, size))
                .map(vacancyMapper::mapToDto);

        return pageHolderWrapper.wrapPageHolder(userRespondedVacancies);
    }

    @Override
    public Long findAuthUserCreatedVacanciesQuantity() {
        return vacancyRepository.findUserCreatedVacanciesQuantity(authorizedUserService.getAuthorizedUserId())
                .orElse(0L);
    }

    @Override
    public Optional<VacancyDto> findUserLastCreatedVacancy() {
        if (!authorizedUserService.isUserAuthorized())
            return Optional.empty();

        return vacancyRepository.findUserCreatedLastVacancy(authorizedUserService.getAuthorizedUserId())
                .map(vacancyMapper::mapToDto);
    }
}
