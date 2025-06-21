package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsCategoryIdExists;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsCategoryIdExistsValidator implements ConstraintValidator<IsCategoryIdExists, Long> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null || id < 0)
            return false;

        return categoryService.findAllCategories().stream()
                .anyMatch(category -> category.getId().equals(id) ||
                        (category.getParentId() != null && category.getParentId().equals(id)));
    }
}
