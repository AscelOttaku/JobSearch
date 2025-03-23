package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.impl.CategoryMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    public CategoryDto findVacancyById(Long vacancyId) {
        return categoryDao.findCategoryById(vacancyId)
                .map(categoryMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Category not found"));
    }

    public boolean checkIfCategoryExistsById(Long categoryId) {
        return categoryDao.findCategoryById(categoryId).isPresent();
    }
}
