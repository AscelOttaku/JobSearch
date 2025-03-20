package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.attractor.jobsearch.util.validater.Validator.isValidVacancy;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyDao vacancyDao;

    @Override
    public VacancyDto findVacancyById(Long vacancyId) {
        return vacancyDao.findVacancyById(vacancyId)
                .map(vacancyMapper::mapToDto)
                .orElseThrow(() -> new VacancyNotFoundException("vacancy by id " + vacancyId + " not found"));
    }

    @Override
    public boolean isVacancyExist(Long vacancyId) {
        try {
            return findVacancyById(vacancyId) != null;
        } catch (VacancyNotFoundException e) {
            return false;
        }
    }

    @Override
    public Long createVacancy(VacancyDto vacancyDto) {
        if (!isValidVacancy(vacancyDto))
            throw new IllegalArgumentException("vacancy dto invalid");

        return vacancyDao.createVacancy(vacancyMapper.mapToEntity(vacancyDto));
    }

    @Override
    public Long updateVacancy(VacancyDto vacancyDto) {
        if (!isValidVacancy(vacancyDto))
            throw new IllegalArgumentException("vacancy dto invalid");

        Vacancy vacancy = vacancyMapper.mapToEntity(vacancyDto);
        return vacancyDao.updateVacancy(vacancy);
    }

    @Override
    public boolean deleteVacancy(Long vacancyId) {
        return vacancyDao.deleteVacancyById(vacancyId);
    }

    @Override
    public List<VacancyDto> findAllActiveVacancies() {
        return vacancyDao.findAllActiveVacancies().stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findVacanciesByCategory(Category category) {
        if (Validator.isNotValid(category))
            throw new IllegalArgumentException("Category is empty");

       return vacancyDao.findVacancyByCategory(category.getId()).stream()
               .map(vacancyMapper::mapToDto)
               .toList();
    }

    @Override
    public List<VacancyDto> findUserRespondedVacancies(UserDto userDto) {
        if (Validator.isNotValidUser(userDto))
            throw new IllegalArgumentException("Not a JobSeeker");

        return vacancyDao.findVacanciesByUserEmail(userDto.getEmail()).stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }

    @Override
    public List<VacancyDto> findAllVacancies() {
        return vacancyDao.findAllVacancies().stream()
                .map(vacancyMapper::mapToDto)
                .toList();
    }
}
