package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsCategoryNameExists;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IsCategoryNameExistsValidator implements ConstraintValidator<IsCategoryNameExists, String> {
    private final CategoryService categoryService;

    @Override
    public boolean isValid(String arg, ConstraintValidatorContext constraintValidatorContext) {
        return categoryService.findAllCategories().stream()
                .anyMatch(category -> category.getName().equalsIgnoreCase(arg));
    }
}
