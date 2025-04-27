package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.ContactInfoValidEmailFormatValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ContactInfoValidEmailFormatValidator.class)
public @interface ContactInfoEmailValidEmail {
    String message() default "email format is incorrect";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
