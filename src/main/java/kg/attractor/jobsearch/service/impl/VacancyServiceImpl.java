package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyDao vacancyDao;
    private final CategoryService categoryService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public VacancyDto findVacancyById(Long vacancyId) {
        Validator.isValidId(vacancyId);

        return vacancyDao.findVacancyById(vacancyId)
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

        try {
            findVacancyById(vacancyId);
            return true;
        } catch (VacancyNotFoundException e) {
            return false;
        }
    }

    @Override
    public VacancyDto createdVacancy(VacancyDto vacancyDto) {
        UserDto authorizedUser = authorizedUserService.getAuthorizedUser();

        log.info("Create vacancy / user name: {}", authorizedUser.getName());

        var getCategory = categoryService.checkIfCategoryExistsById(vacancyDto.getCategoryId());

        if (!getCategory)
            throw new CustomIllegalArgException(
                    "Field id category is not exits",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("categoryId")
                            .rejectedValue(vacancyDto.getCategoryId())
                            .build()
            );

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        vacancy.setVacancyUserId(authorizedUser.getUserId());

        var vacancyId = vacancyDao.createVacancy(vacancy);

        return findVacancyById(vacancyId);
    }

    @Override
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Validator.isValidId(vacancyDto.getVacancyId());

        UserDto user = authorizedUserService.getAuthorizedUser();

        log.info("Updated vacancy / user name: {}", user.getName());

        if (!isVacancyExistById(vacancyDto.getVacancyId()))
            throw new EntityNotFoundException(
                    "Entity vacancy doesn't found by id",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(vacancyDto.getVacancyId())
                            .build()
            );

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        vacancy.setVacancyUserId(user.getUserId());

        var updatedVacancyId = vacancyDao.updateVacancy(vacancy);
        return findVacancyById(updatedVacancyId);
    }


    @Override
    public void deleteVacancy(Long vacancyId) {
        Validator.isValidId(vacancyId);

        boolean res = vacancyDao.deleteVacancyById(vacancyId);

        if (!res)
            throw new VacancyNotFoundException(
                    "vacancy by id " + vacancyId + " not found",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(vacancyId)
                            .build()
            );
    }

    @Override
    public List<VacancyDto> findAllActiveVacancies() {
        return vacancyDao.findAllActiveVacancies().stream()
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

        return vacancyDao.findVacancyByCategory(id).stream()
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

        return vacancyDao.findVacanciesByUserEmail(user.getEmail()).stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findAllVacancies() {
        return vacancyDao.findAllVacancies().stream()
                .sorted(Comparator.comparing(this::getLastTimeOfVacancy).reversed())
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    private LocalDateTime getLastTimeOfVacancy(Vacancy vacancy) {
        if (vacancy.getUpdated() == null)
            return vacancy.getCreated();

        return vacancy.getUpdated();
    }

    public boolean isVacancyExistById(Long id) {
        return vacancyDao.findVacancyById(id).isPresent();
    }

    @Override
    public Long findVacancyOwnerIdByVacancyId(Long vacancyId) {
        Optional<Vacancy> vacancy = vacancyDao.findVacancyById(vacancyId);

        return vacancy
                .map(Vacancy::getVacancyUserId)
                .orElse(-1L);
    }

    @Override
    public List<VacancyDto> findUserCreatedVacancies() {
        Long userId = authorizedUserService.getAuthorizedUserId();

        return vacancyDao.findUserCreatedVacanciesByUserId(userId)
                .stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public VacancyDto findAuthorizedUsersVacancyById(Long vacancyId) {
        Long authorizedUserId = authorizedUserService.getAuthorizedUserId();
        VacancyDto vacancyDto = findVacancyById(vacancyId);

        if (!Objects.equals(vacancyDto.getUserId(), authorizedUserId)) {
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
}
