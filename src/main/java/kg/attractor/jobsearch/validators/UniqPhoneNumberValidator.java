package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.UniquePhoneNumber;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class UniqPhoneNumberValidator implements ConstraintValidator<UniquePhoneNumber, String> {
    private final UserService userService;

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return userService.isUserPhoneNumberUnique(phoneNumber);
    }
}
