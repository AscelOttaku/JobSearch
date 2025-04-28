package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.CategoryMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.repository.CategoryRepository;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public boolean checkIfCategoryExistsById(Long categoryId) {
        return categoryRepository.findById(categoryId).isPresent();
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(categoryMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Category not found by id: " + categoryId));
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapToDto)
                .toList();
    }

    @Override
    public String findCategoryNameById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .map(Category::getName)
                .orElseThrow(() -> new NoSuchElementException("Category not found by id " + categoryId));
    }
}
