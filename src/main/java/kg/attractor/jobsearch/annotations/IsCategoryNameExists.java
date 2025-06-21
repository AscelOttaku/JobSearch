package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.annotations.validators.IsCategoryNameExistsValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = IsCategoryNameExistsValidator.class)
public @interface IsCategoryNameExists {
    String message() default "category is not exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
