package kg.attractor.jobsearch.validators;

import jakarta.validation.*;
import jakarta.validation.Validator;
import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.converters.FileSupportConverter;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeValidator {
    private final Validator validator;
    private final FileSupportConverter fileSupportConverter;

    public void isValid(ResumeDto resumeDto, BindingResult bindingResult) {
        Set<ConstraintViolation<ResumeDto>> constraintViolations = validator.validate(resumeDto);

        boolean isWorkExperiencesValid = resumeDto.getWorkExperienceInfoDtos()
                .stream()
                .noneMatch(this::isNotEmptyWorkExperience);

        boolean isEducationalInfosValid = resumeDto.getEducationInfoDtos()
                .stream()
                .noneMatch(this::isNotEmptyEducationalInfo);

        Set<ConstraintViolation<ResumeDto>> getListConstraintViolations = constraintViolations
                .stream()
                .filter(constraintValidator -> {
                    String field = constraintValidator.getPropertyPath().toString();

                    if (field.contains("workExperienceInfoDtos"))
                        return !isWorkExperiencesValid;

                    if (field.contains("educationalInfoDtos"))
                        return !isEducationalInfosValid;

                    return false;
                })
                .collect(Collectors.toSet());

        for (ConstraintViolation<ResumeDto> constraintViolation : getListConstraintViolations) {
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();

            bindingResult.rejectValue(field, "resume_error", message);
        }
    }

    private boolean isNotEmptyWorkExperience(WorkExperienceInfoDto workExperienceInfoDto) {
        return workExperienceInfoDto.getYears() != null ||
                workExperienceInfoDto.getPosition() != null && !workExperienceInfoDto.getPosition().isBlank() ||
                workExperienceInfoDto.getCompanyName() != null && !workExperienceInfoDto.getCompanyName().isBlank() ||
                workExperienceInfoDto.getResponsibilities() != null && !workExperienceInfoDto.getResponsibilities().isBlank();
    }

    private boolean isNotEmptyEducationalInfo(EducationalInfoDto educationalInfoDto) {
        return educationalInfoDto.getDegree() != null && !educationalInfoDto.getDegree().isBlank() ||
                educationalInfoDto.getInstitution() != null && !educationalInfoDto.getInstitution().isBlank() ||
                educationalInfoDto.getProgram() != null && !educationalInfoDto.getProgram().isBlank();
    }
}
