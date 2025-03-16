package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.service.VacancyService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VacancyServiceImpl implements VacancyService {

    @Override
    public Optional<VacancyDto> findVacancyById(Long vacancyId) {
        //ToDO find vacancy by id logic

        return Optional.empty();
    }

    @Override
    public boolean createVacancy(VacancyDto vacancyDto) {
        //ToDo create vacancy logic

        return true;
    }

    @Override
    public boolean updateVacancy(VacancyDto vacancyDto) {
        //ToDo update vacancy logic

        return true;
    }

    @Override
    public boolean deleteVacancy(Long vacancyId) {
        //ToDo delete vacancy logic

        return true;
    }

    public List<VacancyDto> findActiveVacancies() {
        //ToDo find all active vacancies

        return Collections.emptyList();
    }

    public Optional<VacancyDto> findVacanciesByCategory(String category) {
        //ToDo find vacancy by category

        return Optional.empty();
    }

    public boolean createRespond(Long vacancyId, Long resumeId) {
        //ToDo create respondedApplication data

        return true;
    }
}
