package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.ContactTypeExistValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = ContactTypeExistValidator.class)
public @interface IsContactTypeExist {
    String message() default "contact type is not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
