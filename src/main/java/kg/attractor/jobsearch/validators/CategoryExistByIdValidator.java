package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.CategoryExistById;
import kg.attractor.jobsearch.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryExistByIdValidator implements ConstraintValidator<CategoryExistById, Long> {
    private final CategoryService categoryService;

    @Override
    public void initialize(kg.attractor.jobsearch.annotations.CategoryExistById constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
       return categoryService.checkIfCategoryExistsById(id);
    }
}
