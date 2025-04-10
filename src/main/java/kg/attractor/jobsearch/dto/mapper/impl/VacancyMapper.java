package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VacancyMapper implements Mapper<VacancyDto, Vacancy> {
    private final CategoryService categoryService;

    @Override
    public VacancyDto mapToDto(Vacancy entity) {
        return VacancyDto.builder()
                .vacancyId(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .categoryId(entity.getCategoryId())
                .categoryName(categoryService.findCategoryNameById(entity.getCategoryId()))
                .salary(entity.getSalary() == null ? 0 : entity.getSalary())
                .expFrom(entity.getExpFrom())
                .expTo(entity.getExpTo())
                .isActive(entity.getIsActive())
                .userId(entity.getVacancyUserId())
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
