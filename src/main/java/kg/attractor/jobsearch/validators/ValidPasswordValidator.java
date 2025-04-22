package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (password == null || password.isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "password is null or blank"
            )
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        if (!password.matches("^(?=.*[A-Z])(?=.*\\d).+$")) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "password format is incorrect"
                    )
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
