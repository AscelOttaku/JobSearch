package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.UniqueCategoryValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueCategoryValidator.class)
public @interface UniqueCategory {
    String message() default "value is already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
