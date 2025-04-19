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

        if (password == null || password.isBlank()) {
            String previousPassword = userService.findUserById(userId).getPassword();

            if (previousPassword == null || previousPassword.isBlank())
                return false;

            userDto.setPassword(previousPassword);
            return true;
        }

        return password.matches("^(?=.*[A-Z])(?=.*\\d).+$");
    }
}
