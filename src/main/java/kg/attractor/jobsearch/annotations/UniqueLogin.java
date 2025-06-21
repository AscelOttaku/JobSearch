package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.annotations.validators.ChannelUniqueLoginValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ChannelUniqueLoginValidator.class)
public @interface UniqueLogin {
    String message() default "login must be unique";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
