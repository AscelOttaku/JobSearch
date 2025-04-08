package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.CategoryExistByIdValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CategoryExistByIdValidator.class)
public @interface CategoryExistById {
    String message() default "category does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
