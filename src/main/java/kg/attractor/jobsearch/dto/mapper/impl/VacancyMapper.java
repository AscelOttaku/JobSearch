package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Vacancy;
import org.springframework.stereotype.Service;

@Service
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
                .isActive(entity.getIsActive())
                .userId(entity.getUserId())
                .created(entity.getCreated())
                .updated(entity.getUpdated())
                .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDto.getId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.isActive());
        vacancy.setUserId(vacancyDto.getUserId());
        vacancy.setCreated(vacancyDto.getCreated());
        vacancy.setUpdated(vacancyDto.getUpdated());
        return vacancy;
    }
}
