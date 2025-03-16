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
    public boolean createRespond(Long vacancyId, Long resumeId) {
        //ToDo create respond by taking vacancy id and resume id

        return true;
    }
}
