package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.SkillMapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.service.CategoryService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class VacancyMapper implements Mapper<VacancyDto, Vacancy> {
    private final CategoryService categoryService;
    private final SkillMapper skillMapper;

    @Override
    public VacancyDto mapToDto(Vacancy vacancy) {

        UserDto userDto = UserDto.builder()
                .userId(vacancy.getUser().getUserId())
                .name(vacancy.getUser().getName())
                .surname(vacancy.getUser().getSurname())
                .accountType(vacancy.getUser().getAccountType())
                .avatar(vacancy.getUser().getAvatar())
                .build();

        return VacancyDto.builder()
                .vacancyId(vacancy.getId())
                .name(vacancy.getName())
                .description(vacancy.getDescription())
                .categoryId(vacancy.getCategory().getId())
                .categoryName(categoryService.findCategoryNameById(vacancy.getCategory().getId()))
                .salary(vacancy.getSalary() == null ? 0 : vacancy.getSalary())
                .expFrom(vacancy.getExpFrom())
                .expTo(vacancy.getExpTo())
                .isActive(vacancy.getIsActive())
                .user(userDto)
                .created(Util.dateTimeFormat(vacancy.getCreated()))
                .updated(Util.dateTimeFormat(vacancy.getUpdated()))
                .respondedApplications(vacancy.getRespondedApplications().stream()
                        .map(respondedApplication -> RespondApplicationDto.builder()
                                .resumeId(respondedApplication.getResume().getId())
                                .confirmation(respondedApplication.getConfirmation())
                                .build())
                        .toList())
                .skills(vacancy.getSkills().stream()
                        .map(skillMapper::mapToDto)
                        .toList())
                .build();
    }

    @Override
    public Vacancy mapToEntity(VacancyDto vacancyDto) {
        Category category = new Category();
        category.setId(vacancyDto.getCategoryId());

        User user = new User();
        user.setUserId(vacancyDto.getUser().getUserId());

        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDto.getVacancyId());
        vacancy.setName(vacancyDto.getName());
        vacancy.setDescription(vacancyDto.getDescription());
        vacancy.setCategory(category);
        vacancy.setSalary(vacancyDto.getSalary());
        vacancy.setExpFrom(vacancyDto.getExpFrom());
        vacancy.setExpTo(vacancyDto.getExpTo());
        vacancy.setIsActive(vacancyDto.isActive());
        vacancy.setUser(user);
        vacancy.setCreated(vacancyDto.getCreated() != null ?
                LocalDateTime.parse(vacancyDto.getCreated(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                LocalDateTime.now());
        vacancy.setUpdated(vacancyDto.getUpdated() != null ?
                LocalDateTime.parse(vacancyDto.getUpdated(),
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) :
                LocalDateTime.now());
        vacancy.setSkills(vacancyDto.getSkills() != null ?
                vacancyDto.getSkills().stream()
                        .map(skillMapper::mapToEntity)
                        .toList() : new ArrayList<>());
        return vacancy;
    }
}
