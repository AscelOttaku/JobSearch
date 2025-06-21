package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.annotations.validators.RespondNotExistValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = RespondNotExistValidator.class)
public @interface ValidateRespondNotExist {
    String message() default "respond was made for this vacancy";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
