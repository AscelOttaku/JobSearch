package kg.attractor.jobsearch.service.impl;

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
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
        User authorizedUser = new User();
        authorizedUser.setUserId(authorizedUserService.getAuthorizedUserId());

        log.info("Create vacancy / user name: {}", authorizedUser.getName());

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        vacancy.setUser(authorizedUser);

        return vacancyMapper.mapToDto(vacancyRepository.save(vacancy));
    }

    @Override
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {
        Validator.isValidId(vacancyDto.getVacancyId());

        User user = new User();
        user.setUserId(authorizedUserService.getAuthorizedUserId());

        log.info("Updated vacancy / user name: {}", user.getName());

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        vacancy.setUser(user);

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
    public List<VacancyDto> findAllVacancies() {
        return vacancyRepository.findAll().stream()
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
    public List<VacancyDto> findUserCreatedVacancies() {
        Long userId = authorizedUserService.getAuthorizedUserId();

        return vacancyRepository.findUserVacanciesByUserId(userId)
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
}
