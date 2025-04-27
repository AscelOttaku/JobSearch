package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsContactTypeExist;
import kg.attractor.jobsearch.service.ContactTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ContactTypeExistValidator implements ConstraintValidator<IsContactTypeExist, String> {
    private final ContactTypeService contactTypeService;

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        return contactTypeService.findAllContactTypes().stream()
                .anyMatch(contactTypeDto -> contactTypeDto.getType().equals(type));
    }
}
