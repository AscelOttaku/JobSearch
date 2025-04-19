package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeValidator {
    private final Validator validator;

    public void isValid(ResumeDto resumeDto, BindingResult bindingResult) {
        Set<ConstraintViolation<ResumeDto>> constraintViolations = validator.validate(resumeDto);

        boolean isWorkExperiencesValid = resumeDto.getWorkExperienceInfoDtos()
                .stream()
                .noneMatch(kg.attractor.jobsearch.validators.Validator::isNotEmptyWorkExperience);

        boolean isEducationalInfosValid = resumeDto.getEducationInfoDtos()
                .stream()
                .noneMatch(kg.attractor.jobsearch.validators.Validator::isNotEmptyEducationalInfo);

        Set<ConstraintViolation<ResumeDto>> getListConstraintViolations = constraintViolations
                .stream()
                .filter(constraintValidator -> {
                    String field = constraintValidator.getPropertyPath().toString().toLowerCase();

                    if (field.contains("workExperienceInfoDtos".toLowerCase()))
                        return !isWorkExperiencesValid;

                    if (field.contains("educationInfoDtos".toLowerCase()))
                        return !isEducationalInfosValid;

                    return false;
                })
                .collect(Collectors.toSet());

        for (ConstraintViolation<ResumeDto> constraintViolation : constraintViolations) {
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();

            if (field.toLowerCase().contains("workExperienceInfoDtos".toLowerCase()) ||
                    field.toLowerCase().contains("educationInfoDtos".toLowerCase()))
                continue;

            bindingResult.rejectValue(field, "resume_error", message);
        }

        getListConstraintViolations.forEach(constraintViolation -> {
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();

            bindingResult.rejectValue(field, "resume_error", message);
        });
    }
}