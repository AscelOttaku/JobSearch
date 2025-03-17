package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class VacancyServiceImpl implements VacancyService {

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
    public Optional<List<VacancyDto>> findVacanciesByCategory(String category) {
        //ToDo find vacancy by category

        return Optional.empty();
    }

    @Override
    public Long createRespond(Long vacancyId, Long resumeId) {
        //ToDo create respond by taking vacancy id and resume id
        //return id of created object

        return -1L;
    }
}
