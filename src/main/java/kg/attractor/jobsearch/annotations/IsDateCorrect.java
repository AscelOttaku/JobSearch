package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.annotations.validators.DateIsBeforeValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = DateIsBeforeValidator.class)
public @interface IsDateCorrect {
    String message() default "date is incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String dateFrom();
    String dateTo();
}
