package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.annotations.validators.NotEmptyMultipartFileValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotEmptyMultipartFileValidator.class)
public @interface NonEmptyMultipartFile {
    String message() default "Файл должен быть загружен";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
