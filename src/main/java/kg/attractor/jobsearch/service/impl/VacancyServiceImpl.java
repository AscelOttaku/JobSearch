package kg.attractor.jobsearch.service.impl;

import jakarta.validation.constraints.Email;
import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyDao vacancyDao;
    private final CategoryService categoryService;
    private final UserService userService;

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
        var getCategory = categoryService.checkIfCategoryExistsById(vacancyDto.getCategoryId());
        var getUserId = userService.checkIfEmployerExistById(vacancyDto.getUserId());

        if (!getCategory)
            throw new CustomIllegalArgException(
                    "Field id category is not exits",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("categoryId")
                            .rejectedValue(vacancyDto.getCategoryId())
                            .build()
            );

        if (!getUserId)
            throw new CustomIllegalArgException(
                    "Field userId is not exists or equals to jobSeeker",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("user_id")
                            .rejectedValue(vacancyDto.getUserId())
                            .build()
            );

        var vacancyId = vacancyDao.createVacancy(vacancyMapper.mapToEntity(vacancyDto));

        return findVacancyById(vacancyId);
    }

    @Override
    public VacancyDto updateVacancy(Long vacancyId, VacancyDto vacancyDto) {
        Validator.isValidId(vacancyId);

        boolean isVacancyExistById = isVacancyExistById(vacancyId);

        if (!isVacancyExistById)
            throw new EntityNotFoundException(
                    "Entity vacancy doesn't found by id",
                    CustomBindingResult.builder()
                            .className(Vacancy.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(vacancyId)
                            .build()
            );

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        vacancy.setId(vacancyId);

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
    public List<VacancyDto> findUserRespondedVacancies(@Email(message = "{email_message}") String userEmail) {
        boolean isUserExistsByEmail = userService.checkIfJobSeekerExistByEmail(userEmail);

        if (isUserExistsByEmail)
            throw new EntityNotFoundException(
                    "User not found by email",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("email")
                            .rejectedValue(userEmail)
                            .build()
            );

        return vacancyDao.findVacanciesByUserEmail(userEmail).stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findAllVacancies() {
        return vacancyDao.findAllVacancies().stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    public boolean isVacancyExistById(Long id) {
        return vacancyDao.findVacancyById(id).isPresent();
    }
}
