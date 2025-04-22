package kg.attractor.jobsearch.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import kg.attractor.jobsearch.validators.ValidateExpFromExpTo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = ValidateExpFromExpTo.class)
public @interface IsExpFromAndExpToCorrectFormat {
    String message() default "values expFrom or ExpTo are not valid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
