package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsContactTypeExist;
import kg.attractor.jobsearch.enums.ContactTypes;

public class ContactTypeExistValidator implements ConstraintValidator<IsContactTypeExist, String> {

    @Override
    public boolean isValid(String type, ConstraintValidatorContext constraintValidatorContext) {
        try {
            ContactTypes.valueOf(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
