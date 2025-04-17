package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
                .categoryId(vacancy.getCategory().getId())
                .categoryName(categoryService.findCategoryNameById(vacancy.getCategory().getId()))
                .salary(vacancy.getSalary() == null ? 0 : vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .userId(vacancy.getUser().getUserId())
                .created(Util.dateTimeFormat(vacancy.getCreated()))
                .updated(Util.dateTimeFormat(vacancy.getUpdated()))
                .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Category category = new Category();
        category.setId(vacancyDto.getCategoryId());

        User user = new User();
        user.setUserId(vacancyDto.getUserId());

        Vacancy vacancy = new Vacancy();
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategory(category);
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.isActive());
        vacancy.setUser(user);
        return vacancy;
    }
}
