package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidPassword;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, UserDto> {
    private final UserService userService;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = userDto.getPassword();
        Long userId = userDto.getUserId();

        if (!Validator.isStringValid(password)) {
            String hasPreviousPassword = userService.findUserPasswordByUserId(userId);

            if (!Validator.isStringValid(hasPreviousPassword)) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "Password cannot be null or blank"
                )
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            }

            return true;
        }

        if (!password.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).+$")) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "password should contains at least one digit"
            )
                    .addPropertyNode("password")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}
