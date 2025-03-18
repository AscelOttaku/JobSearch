package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Vacancy;

public class VacancyMapper implements Mapper<VacancyDto, Vacancy> {
    @Override
    public VacancyDto mapToDto(Vacancy entity) {
        return VacancyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .salary(entity.getSalary())
                .expFrom(entity.getExpFrom())
                .expTo(entity.getExpTo())
                .isActive(entity.isActive())
                .userId(entity.getUserId())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        return Vacancy.builder()
                .id(vacancyDto.getId())
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.isActive())
                .userId(vacancyDto.getUserId())
                .created(vacancyDto.getCreated())
                .updated(vacancyDto.getUpdated())
                .build();
    }
}
