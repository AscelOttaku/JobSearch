package kg.attractor.jobsearch.validators;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Validator {

    public static void isValidId(Long arg) {

        if (arg != null && arg > 0)
            return;

        throw new CustomIllegalArgException(
                "Illegal arg exception",
                CustomBindingResult.builder()
                        .className(Vacancy.class.getSimpleName())
                        .fieldName("id")
                        .rejectedValue(arg)
                        .build()
        );
    }

    public static boolean isValidUserAccountType(User user) {
        return user.getAccountType().equalsIgnoreCase("jobSeeker");
    }

    public boolean isStringNotValid(String arg) {
        return arg == null || arg.isBlank();
    }

    public static boolean isNotEmptyWorkExperience(WorkExperienceInfoDto workExperienceInfoDto) {
        return workExperienceInfoDto.getYears() != null ||
                workExperienceInfoDto.getPosition() != null && !workExperienceInfoDto.getPosition().isBlank() ||
                workExperienceInfoDto.getCompanyName() != null && !workExperienceInfoDto.getCompanyName().isBlank() ||
                workExperienceInfoDto.getResponsibilities() != null && !workExperienceInfoDto.getResponsibilities().isBlank();
    }

    public static boolean isNotEmptyEducationalInfo(EducationalInfoDto educationalInfoDto) {
        return educationalInfoDto.getDegree() != null && !educationalInfoDto.getDegree().isBlank() ||
                educationalInfoDto.getInstitution() != null && !educationalInfoDto.getInstitution().isBlank() ||
                educationalInfoDto.getProgram() != null && !educationalInfoDto.getProgram().isBlank();
    }
}
