package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeValidator {
    private final Validator validator;

    public void isValid(ResumeDto resumeDto, BindingResult bindingResult) {
        Set<ConstraintViolation<ResumeDto>> constraintViolations = validator.validate(resumeDto);

        for (ConstraintViolation<ResumeDto> constraintViolation : constraintViolations) {
            String fieldName = constraintViolation.getPropertyPath().toString();
            boolean isValid = false;

            if (fieldName.toLowerCase().contains("workExperienceInfoDtos".toLowerCase())) {
                Integer index = Util.findNumber(fieldName)
                        .orElse(0);

                if (ValidatorUtil.isEmptyWorkExperience(resumeDto.getWorkExperienceInfoDtos()
                        .get(index))) isValid = true;

            } else if (fieldName.toLowerCase().contains("educationInfoDtos".toLowerCase())) {
                Integer index = Util.findNumber(fieldName)
                        .orElse(0);

                if (ValidatorUtil.isEmptyEducationalInfo(resumeDto.getEducationInfoDtos()
                        .get(index))) isValid = true;
            }

            if (!isValid)
                bindingResult.rejectValue(fieldName, "resume_error", constraintViolation.getMessage());
        }
    }
}