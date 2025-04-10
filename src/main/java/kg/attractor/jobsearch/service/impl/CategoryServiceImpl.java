package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.impl.CategoryMapper;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Override
    public boolean checkIfCategoryExistsById(Long categoryId) {
        return findCategoryById(categoryId).isPresent();
    }

    @Override
    public Optional<CategoryDto> findCategoryById(Long categoryId) {
        return categoryDao.findCategoryById(categoryId)
                .map(categoryMapper::mapToDto);
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
