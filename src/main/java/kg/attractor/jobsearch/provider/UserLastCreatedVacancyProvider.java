package kg.attractor.jobsearch.provider;

import kg.attractor.jobsearch.dto.VacancyDto;

import java.util.Optional;

public interface UserLastCreatedVacancyProvider {
    Optional<VacancyDto> findUserLastCreatedVacancy();
}
