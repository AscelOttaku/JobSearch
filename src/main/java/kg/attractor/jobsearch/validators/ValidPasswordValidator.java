package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidPassword;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;

@Service
@RequiredArgsConstructor
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, UserDto> {
    private final UserService userService;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = userDto.getPassword();
        Long userId = userDto.getUserId();

        if (Validator.isStringNotValid(password)) {

            if (userId == null)
                return getNullContext(constraintValidatorContext, "Password cannot be null or blank");

            String hasPreviousPassword = userService.findUserPasswordByUserId(userId);

            if (Validator.isStringNotValid(hasPreviousPassword))
                return getNullContext(constraintValidatorContext, "Password cannot be null or blank");

            userDto.setPassword(hasPreviousPassword);
            return true;
        }

        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+$")) {
            return getNullContext(constraintValidatorContext, "password should contains at least one digit");
        }
        return true;
    }

    private static boolean getNullContext(ConstraintValidatorContext constraintValidatorContext, String message) {
        constraintValidatorContext.buildConstraintViolationWithTemplate(
                        message
                )
                .addPropertyNode("password")
                .addConstraintViolation();
        return false;
    }
}
