package kg.attractor.jobsearch.annotations.validators;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.ValidateRespondNotExist;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.service.RespondService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondNotExistValidator implements ConstraintValidator<ValidateRespondNotExist, RespondApplicationDto> {
    private final RespondService respondService;

    @Override
    public boolean isValid(RespondApplicationDto respondApplicationDto, ConstraintValidatorContext constraintValidatorContext) {
        if (!respondService.validateRespondNotExist(respondApplicationDto)) {
            constraintValidatorContext.disableDefaultConstraintViolation();

            constraintValidatorContext.buildConstraintViolationWithTemplate(
                    "respond was made for this vacancy by this resume"
            )
                    .addPropertyNode("resumeId")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}
