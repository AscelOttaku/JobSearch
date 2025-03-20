package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.VacancyDao;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

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
