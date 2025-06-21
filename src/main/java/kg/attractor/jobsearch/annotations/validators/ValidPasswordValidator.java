package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidUserPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidPasswordValidator implements ConstraintValidator<ValidUserPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();

        if (password == null || password.isBlank()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "password is null or blank"
                    )
                    .addConstraintViolation();
            return false;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if ((auth == null || !auth.isAuthenticated() ||
                (auth instanceof AnonymousAuthenticationToken)) &&
                !password.matches("^(?=.*[A-Z])(?=.*\\d).+$")) {

            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "password format is incorrect"
                    )
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
