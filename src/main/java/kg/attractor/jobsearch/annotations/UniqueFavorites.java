package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.UniqueFavoritesValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = UniqueFavoritesValidator.class)
public @interface UniqueFavorites {
    String message() default "favorites is not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
