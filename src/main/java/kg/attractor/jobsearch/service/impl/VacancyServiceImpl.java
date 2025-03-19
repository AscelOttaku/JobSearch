package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyServiceImpl implements VacancyService {
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;
    private final VacancyDao vacancyDao;

    @Autowired
    public VacancyServiceImpl(Mapper<VacancyDto, Vacancy> vacancyMapper, VacancyDao vacancyDao) {
        this.vacancyMapper = vacancyMapper;
        this.vacancyDao = vacancyDao;
    }

    @Override
    public Optional<VacancyDto> findVacancyById(Long vacancyId) {
        //ToDO find vacancy by id logic

        return Optional.empty();
    }

    @Override
    public Long createVacancy(VacancyDto vacancyDto) {
        //ToDo create vacancy logic
        //return id of created object

        return -1L;
    }

    @Override
    public Long updateVacancy(VacancyDto vacancyDto) {
        //ToDo update vacancy logic
        //return id of created object

        return -1L;
    }

    @Override
    public boolean deleteVacancy(Long vacancyId) {
        //ToDo delete vacancy logic
        //return id of created object

        return true;
    }

    @Override
    public List<VacancyDto> findActiveVacancies() {
        //ToDo find all active vacancies

        return Collections.emptyList();
    }

    @Override
    public List<VacancyDto> findVacanciesByCategory(Category category) {
        if (category == null || category.getId() == null)
            throw new IllegalArgumentException("Category is empty");

       return vacancyDao.findVacancyByCategory(category.getId()).stream()
               .map(vacancyMapper::mapToDto)
               .toList();
    }

    @Override
    public Long createRespond(Long vacancyId, Long resumeId) {
        //ToDo create respond by taking vacancy id and resume id
        //return id of created object

        return -1L;
    }

    @Override
    public List<VacancyDto> findUserRespondedVacancies(User user) {
        if (user == null || !user.getAccountType().equalsIgnoreCase("JobSeeker"))
            throw new IllegalArgumentException("Not a JobSeeker");

        return vacancyDao.findVacanciesByUserid(user.getId()).stream()
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
