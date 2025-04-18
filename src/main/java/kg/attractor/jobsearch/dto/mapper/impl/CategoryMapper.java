package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import org.springframework.stereotype.Service;

@Service
public class CategoryMapper implements Mapper<CategoryDto, Category> {

    @Override
    public CategoryDto mapToDto(Category entity) {
        return CategoryDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .parentId(entity.getUnderCategory() != null ?
                        entity.getUnderCategory().getId() : null)
                .build();
    }

    @Override
    public Category mapToEntity(CategoryDto dto) {
        Category underCategory = new Category();
        underCategory.setId(dto.getParentId());

        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setUnderCategory(underCategory);
        return category;
    }
}
