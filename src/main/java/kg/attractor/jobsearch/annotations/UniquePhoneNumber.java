package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.UniqPhoneNumberValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqPhoneNumberValidator.class)
public @interface UniquePhoneNumber {
    String message() default "Phone number is not unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
