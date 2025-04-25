package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ContactInfoEmailValidEmail;
import kg.attractor.jobsearch.dto.ContactInfoDto;

public class ContactInfoValidEmailFormatValidator implements ConstraintValidator<ContactInfoEmailValidEmail, ContactInfoDto> {

    @Override
    public boolean isValid(ContactInfoDto contactInfoDto, ConstraintValidatorContext constraintValidatorContext) {
        if (contactInfoDto.getContactType().getType().equals("EMAIL")) {
            constraintValidatorContext.disableDefaultConstraintViolation();

            String value = contactInfoDto.getValue();

            if (value == null || value.isBlank())
                return true;

            if (!(value.contains("@") && value.contains("."))) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "email is not contains @ or point its not allowed"
                )
                        .addPropertyNode("value")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
