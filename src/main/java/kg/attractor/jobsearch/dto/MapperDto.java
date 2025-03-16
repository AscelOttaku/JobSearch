package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;

public class MapperDto {
    private MapperDto() {
        throw new IllegalStateException("Utility class");
    }

    public static Vacancy toVacancy(VacancyDto vacancyDto) {
        return Vacancy.builder()
                .id(vacancyDto.getId())
                .name(vacancyDto.getName())
                .description(vacancyDto.getDescription())
                .salary(vacancyDto.getSalary())
                .expFrom(vacancyDto.getExpFrom())
                .expTo(vacancyDto.getExpTo())
                .isActive(vacancyDto.isActive())
                .userId(vacancyDto.getUserId())
                .created(vacancyDto.getCreated())
                .updated(vacancyDto.getUpdated())
                .build();
    }

    public static Resume toResume(ResumeDto resumeDto) {
        return Resume.builder()
                .id(resumeDto.getId())
                .userId(resumeDto.getUserId())
                .name(resumeDto.getName())
                .categoryId(resumeDto.getCategoryId())
                .salary(resumeDto.getSalary())
                .isActive(resumeDto.isActive())
                .created(resumeDto.getCreated())
                .updated(resumeDto.getUpdated())
                .build();
    }

    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getAccountType())
                .build();
    }
}
