package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.VacancyRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyRepository vacancyRepository;
    private final CategoryService categoryService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public VacancyDto findVacancyById(Long vacancyId) {
        Validator.isValidId(vacancyId);

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
        Validator.isValidId(vacancyId);

        return vacancyRepository.findById(vacancyId).isPresent();
    }

    @Override
    public VacancyDto createdVacancy(VacancyDto vacancyDto) {
        UserDto authorizedUser = new UserDto();
        authorizedUser.setUserId(authorizedUserService.getAuthorizedUserId());

        vacancyDto.setUser(authorizedUser);
        log.info("Create vacancy / user name: {}", authorizedUser.getName());

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);

        return vacancyMapper.mapToDto(vacancyRepository.save(vacancy));
    }

    @Override
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Validator.isValidId(vacancyDto.getVacancyId());

        UserDto user = new UserDto();
        user.setUserId(authorizedUserService.getAuthorizedUserId());

        vacancyDto.setUser(user);
        log.info("Updated vacancy / user name: {}", user.getName());

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);

        return vacancyMapper.mapToDto(vacancyRepository.save(vacancy));
    }


    @Override
    public void deleteVacancy(Long vacancyId) {
        Validator.isValidId(vacancyId);

        vacancyRepository.deleteById(vacancyId);
    }

    @Override
    public List<VacancyDto> findAllActiveVacancies() {
        return vacancyRepository.findIsActiveVacancies().stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findVacanciesByCategory(Long id) {
        Validator.isValidId(id);

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

        return PageHolder.<VacancyDto>builder()
                .content(vacanciesPage.stream()
                        .map(vacancyMapper::mapToDto)
                        .toList())
                .page(page)
                .size(pageSize)
                .totalPages(vacanciesPage.getTotalPages())
                .hasNextPage(vacanciesPage.hasNext())
                .hasPreviousPage(vacanciesPage.hasPrevious())
                .build();
    }

    public boolean isVacancyExistById(Long id) {
        return id != null && vacancyRepository.findById(id).isPresent();
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

        Pageable pageable = PageRequest.of(page, size);

        Page<Vacancy> vacanciesPage = vacancyRepository.findUserVacanciesByUserId(userId, pageable);

        return PageHolder.<VacancyDto>builder()
                .content(vacanciesPage.stream()
                        .map(vacancyMapper::mapToDto)
                        .toList())
                .page(page)
                .size(vacanciesPage.getSize())
                .totalPages(vacanciesPage.getTotalPages())
                .hasNextPage(vacanciesPage.hasNext())
                .hasPreviousPage(vacanciesPage.hasPrevious())
                .build();
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

    @Override
    public void updateVacancyDate(Long vacancyId) {
        UserDto authorizedUser = authorizedUserService.getAuthorizedUser();

        Vacancy vacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(() -> new CustomIllegalArgException(
                        "Vacancy not found by " + vacancyId,
                        CustomBindingResult.builder()
                                .className(Vacancy.class.getSimpleName())
                                .fieldName("vacancyId")
                                .rejectedValue(vacancyId)
                                .build()));

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

        return PageHolder.<VacancyDto>builder()
                .content(vacanciesPageHolder.stream()
                        .map(vacancyMapper::mapToDto)
                        .toList())
                .page(page)
                .size(vacanciesPageHolder.getSize())
                .totalPages(vacanciesPageHolder.getTotalPages())
                .hasNextPage(vacanciesPageHolder.hasNext())
                .hasPreviousPage(vacanciesPageHolder.hasPrevious())
                .build();
    }

    @Override
    public List<VacancyDto> findVacanciesByUserId(Long userId) {
        return vacancyRepository.findVacanciesByUserUserId(userId)
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }
}
