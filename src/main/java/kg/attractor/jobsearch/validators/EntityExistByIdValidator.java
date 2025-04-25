package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.service.EntityExistService;
import kg.attractor.jobsearch.enums.EntityType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EntityExistByIdValidator implements ConstraintValidator<EntityExistById, Long> {
    private EntityType entityType;
    private final EntityExistService entityExistService;

    @Override
    public void initialize(EntityExistById constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        entityType = constraintAnnotation.entityType();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        return entityExistService.isEntityExistById(entityType, id);
    }
}
