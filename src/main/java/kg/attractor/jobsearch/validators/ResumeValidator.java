package kg.attractor.jobsearch.validators;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeValidator {
    private final Validator validator;

    public void isValid(ResumeDto resumeDto, BindingResult bindingResult) {
        Set<ConstraintViolation<ResumeDto>> constraintViolations = validator.validate(resumeDto);

        List<String> emptyObjectsNames = new ArrayList<>();

        for (ConstraintViolation<ResumeDto> constraintViolation : constraintViolations) {
            String fieldName = constraintViolation.getPropertyPath().toString();
            boolean isCurrentFieldNameIsEmptyObject = emptyObjectsNames.stream()
                    .anyMatch(fieldName::startsWith);

            if (isCurrentFieldNameIsEmptyObject) continue;

            if (fieldName.contains("workExperienceInfoDtos")) {
                Predicate<Object> validateWorkExperience = arg -> ValidatorUtil.isEmptyWorkExperience((WorkExperienceInfoDto) arg);
                boolean result = validateData(
                        fieldName,
                        resumeDto.getWorkExperienceInfoDtos(),
                        validateWorkExperience
                );

                if (result) {
                    isCurrentFieldNameIsEmptyObject = true;
                    emptyObjectsNames.add(fieldName.substring(0, getIndex(fieldName)));
                }

            } else if (fieldName.contains("educationInfoDtos")) {
                Predicate<Object> validateEducationInfo = arg -> ValidatorUtil.isEmptyEducationalInfo((EducationalInfoDto) arg);
                boolean result = validateData(
                        fieldName,
                        resumeDto.getEducationInfoDtos(),
                        validateEducationInfo
                );

                if (result) {
                    isCurrentFieldNameIsEmptyObject = true;
                    emptyObjectsNames.add(fieldName.substring(0, getIndex(fieldName)));
                }
            }

            if (!isCurrentFieldNameIsEmptyObject)
                bindingResult.rejectValue(fieldName, "resume_error", constraintViolation.getMessage());
        }
    }

    private int getIndex(String fieldName) {
        int index = fieldName.lastIndexOf(".");
        index = index >= 0 ? index : fieldName.length();
        return index;
    }

    public boolean validateData(
            String fieldName,
            List<?> data,
            Predicate<Object> predicate
    ) {
        int index = Util.findNumber(fieldName)
                .orElse(0);

        if (data.isEmpty() || index >= data.size())
            return false;

        Object object = data.get(index);
        return predicate.test(object);
    }
}