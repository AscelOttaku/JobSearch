package kg.attractor.jobsearch.util.validater;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.model.Category;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class Validator {

    public static boolean isValidResume(ResumeDto resumeDto) {
        return resumeDto != null &&
                isNotNullAndIsNotBlank(resumeDto.getName()) &&
                isValidNumber(resumeDto.getCategoryId()) &&
                isValidNumber(resumeDto.getUserId());
    }

    public static boolean isNotValidUser(UserDto userDto) {
        if (userDto == null)
            return true;

        Stream<String> stream = Stream.of(
                userDto.getName(),
                userDto.getSurname(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getPhoneNumber(),
                userDto.getAccountType()
        );

        return !stream.allMatch(Validator::isNotNullAndIsNotBlank);
    }

    private static boolean isNotNullAndIsNotBlank(String string) {
        return string != null && !string.isBlank();
    }

    public static boolean isValidVacancy(VacancyDto vacancyDto) {
        return vacancyDto != null
                && (isNotNullAndIsNotBlank(vacancyDto.getName()))
                && (isValidNumber(vacancyDto.getUserId()))
                && (isValidNumber(vacancyDto.getCategoryId()));
    }

    public static boolean isNotValid(Category category) {
        return category == null || category.getId() == null;
    }

    public static boolean isValidRespondApplication(RespondApplicationDto respondApplicationDto) {
        return respondApplicationDto != null &&
                isValidNumber(respondApplicationDto.getResumeId()) &&
                isValidNumber(respondApplicationDto.getVacancyId());
    }

    private static boolean isValidNumber(Long number) {
        return number != null && number > 0;
    }
}
