package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.CategoryDao;
import kg.attractor.jobsearch.dto.CategoryDto;
import kg.attractor.jobsearch.dto.mapper.impl.CategoryMapper;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

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
        try {
            return Optional.of(categoryMapper.mapToDto(categoryDao.findCategoryById(categoryId)));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
