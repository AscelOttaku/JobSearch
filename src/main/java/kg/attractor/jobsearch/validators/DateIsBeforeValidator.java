package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.IsDateCorrect;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Optional;

public class DateIsBeforeValidator implements ConstraintValidator<IsDateCorrect, Object> {
    private String dateFrom;
    private String dateTo;

    @Override
    public void initialize(IsDateCorrect constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        dateFrom = constraintAnnotation.dateFrom();
        dateTo = constraintAnnotation.dateTo();
    }

    @Override
    public boolean isValid(Object arg, ConstraintValidatorContext constraintValidatorContext) {
        Optional<LocalDateTime> fromDate = getLocalDateTimeFromObject(dateFrom, arg);
        Optional<LocalDateTime> toDate = getLocalDateTimeFromObject(dateTo, arg);

        if (fromDate.isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "{from_date_must_not_be_empty}"
                    )
                    .addPropertyNode("startDate")
                    .addConstraintViolation();
            return false;
        }

        if (toDate.isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                            "{to_date_must_not_be_empty}"
                    )
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            return false;
        }

        if (!fromDate.get().isBefore(toDate.get())) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "fromDate must be before toDate"
            )
                    .addPropertyNode("startDate")
                    .addConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "toDate must be after fromDate"
            )
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }

    private Optional<LocalDateTime> getLocalDateTimeFromObject(String fieldName, Object object) {
        try {
            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return Optional.ofNullable((LocalDateTime) field.get(object));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return Optional.empty();
        }
    }
}
