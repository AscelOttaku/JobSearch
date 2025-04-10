package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CategoryDto;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    boolean checkIfCategoryExistsById(Long categoryId);

    Optional<CategoryDto> findCategoryById(Long categoryId);

    List<CategoryDto> findAllCategories();

    String findCategoryNameById(Long categoryId);
}
