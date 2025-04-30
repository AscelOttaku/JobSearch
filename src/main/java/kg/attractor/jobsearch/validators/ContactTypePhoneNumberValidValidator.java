package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ContactInfoPhoneNumberValid;
import kg.attractor.jobsearch.dto.ContactInfoDto;

public class ContactTypePhoneNumberValidValidator implements ConstraintValidator<ContactInfoPhoneNumberValid, ContactInfoDto> {

    @Override
    public boolean isValid(ContactInfoDto contactInfoDto, ConstraintValidatorContext constraintValidatorContext) {
        if (contactInfoDto.getContactType().getType().equals("PHONE_NUMBER")) {
            constraintValidatorContext.disableDefaultConstraintViolation();

            String value = contactInfoDto.getContactValue();

            if (value == null || value.isBlank() || value.equals("+996"))
                return true;

            if (!value.matches("^\\+?[0-9\\-\\s]+$") || value.length() != 13) {
                constraintValidatorContext.buildConstraintViolationWithTemplate(
                        "phone number is not valid"
                )
                        .addPropertyNode("contactValue")
                        .addConstraintViolation();
                return false;
            }
        }

        return true;
    }
}
