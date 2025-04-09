package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.util.EntityType;
import kg.attractor.jobsearch.validators.EntityExistByIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = EntityExistByIdValidator.class)
public @interface EntityExistById {
    String message() default "category does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    EntityType entityType();
}
