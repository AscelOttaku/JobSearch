package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsExpFromAndExpToCorrectFormat;
import kg.attractor.jobsearch.dto.VacancyDto;
import org.springframework.stereotype.Service;

@Service
public class ValidateExpFromExpTo implements ConstraintValidator<IsExpFromAndExpToCorrectFormat, VacancyDto> {
    @Override
    public boolean isValid(VacancyDto vacancyDto, ConstraintValidatorContext constraintValidatorContext) {
        boolean isValid = true;

        if (vacancyDto.getExpFrom() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("expFrom is null")
                    .addPropertyNode("expFrom")
                    .addConstraintViolation();
            isValid = false;
        }

        if (vacancyDto.getExpTo() == null) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("expTo is null")
                    .addPropertyNode("expTo")
                    .addConstraintViolation();
            isValid = false;
        }

        if (isValid)
            return vacancyDto.getExpFrom().compareTo(vacancyDto.getExpTo()) < 0;

        return isValid;
    }
}
