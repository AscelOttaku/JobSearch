package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class VacancyMapper implements Mapper<VacancyDto, Vacancy> {
    private final CategoryService categoryService;

    @Override
    public VacancyDto mapToDto(Vacancy vacancy) {
        return VacancyDto.builder()
                .vacancyId(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategoryId())
                .categoryName(categoryService.findCategoryNameById(vacancy.getCategoryId()))
                .salary(vacancy.getSalary() == null ? 0 : vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .userId(vacancy.getVacancyUserId())
                .created(Util.dateTimeFormat(vacancy.getCreated()))
                .updated(Util.dateTimeFormat(vacancy.getUpdated()))
                .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategoryId(vacancyDto.getCategoryId());
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.isActive());
        vacancy.setVacancyUserId(vacancyDto.getUserId());
        return vacancy;
    }
}
