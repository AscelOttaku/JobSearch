package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.impl.CategoryMapper;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public boolean checkIfCategoryExistsById(Long categoryId) {
        return categoryDao.findCategoryById(categoryId).isPresent();
    }

    @Override
    public CategoryDto findCategoryById(Long categoryId) {
        return categoryDao.findCategoryById(categoryId)
                .map(categoryMapper::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category by id " + categoryId + " not found",
                        CustomBindingResult.builder()
                                .className("category")
                                .fieldName("id")
                                .rejectedValue(categoryId)
                                .build()
                ));
    }

    @Override
    public List<CategoryDto> findAllCategories() {
        return categoryDao.findAllCategories().stream()
                .map(categoryMapper::mapToDto)
                .toList();
    }

    @Override
    public String findCategoryNameById(Long categoryId) {
        return categoryDao.findCategoryNameById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("Category not found by id " + categoryId));
    }
}
