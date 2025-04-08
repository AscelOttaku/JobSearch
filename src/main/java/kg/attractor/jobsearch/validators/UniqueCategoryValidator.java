package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.UniqueCategory;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, String> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(String arg, ConstraintValidatorContext constraintValidatorContext) {
        return categoryService.findAllCategories().stream()
                .noneMatch(category -> category.getName().equalsIgnoreCase(arg));
    }
}
