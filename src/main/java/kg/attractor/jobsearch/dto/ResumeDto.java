package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.IsCategoryIdExists;
import kg.attractor.jobsearch.model.Skill;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long id;

    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 40, message = "{3_30_size_message}")
    private String name;

    private Long userId;

    @IsCategoryIdExists
    private Long categoryId;

    private String categoryName;

    @PositiveOrZero(message = "{non_negative_message}")
    private double salary;

    private boolean isActive;
    private String created;
    private String updated;

    private List<@Valid EducationalInfoDto> educationInfoDtos;
    private List<@Valid WorkExperienceInfoDto> workExperienceInfoDtos;
    private List<@Valid ContactInfoDto> contactInfos;
    private List<@Valid SkillDto> skills;
}