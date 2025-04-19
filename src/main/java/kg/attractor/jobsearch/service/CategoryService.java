package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    boolean checkIfCategoryExistsById(Long categoryId);

    CategoryDto findCategoryById(Long categoryId);

    List<CategoryDto> findAllCategories();

    String findCategoryNameById(Long categoryId);
}
