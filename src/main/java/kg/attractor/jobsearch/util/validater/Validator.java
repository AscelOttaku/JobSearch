package kg.attractor.jobsearch.util.validater;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import lombok.experimental.UtilityClass;

import java.util.stream.Stream;

@UtilityClass
public class Validator {

    public static boolean isValidResume(UpdateResumeDto resumeDto) {
        return resumeDto != null &&
                isNotNullAndIsNotBlank(resumeDto.getName()) &&
                isValidNumber(resumeDto.getCategoryId()) &&
                isValidNumber(resumeDto.getUserId());
    }

    public static boolean isValidResume(CreateResumeDto resumeDto) {
        return resumeDto != null &&
                isNotNullAndIsNotBlank(resumeDto.getName()) &&
                isValidNumber(resumeDto.getCategoryId()) &&
                isValidNumber(resumeDto.getUserId());
    }

    public static boolean isNotValidUser(UserDto userDto) {
        if (isNotValidData(userDto))
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

    public static boolean isValidUserAccountType(User user) {
        return user.getAccountType().equalsIgnoreCase("jobSeeker");
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

    public static <T> boolean isNotValidData(T arg) {
        return arg == null;
    }

    private static boolean isValidNumber(Long number) {
        return number != null && number > 0;
    }
}
