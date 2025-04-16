package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidPassword;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, UserDto> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(UserDto userDto, ConstraintValidatorContext constraintValidatorContext) {
        String password = userDto.getPassword();
        Long userId = userDto.getUserId();

        if (Validator.isStringNotValid(password)) {
            String hasPreviousPassword = userRepository.findUserPasswordByUserId(userId)
                    .map(User::getPassword)
                    .orElse("");


            if (hasPreviousPassword.isBlank()) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "Password cannot be null or blank"
                )
                        .addPropertyNode("password")
                        .addConstraintViolation();
                return false;
            }
            userDto.setPassword(hasPreviousPassword);
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
