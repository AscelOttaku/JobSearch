package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.enums.EntityType;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto {
    private Long id;

    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 40, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^[\\p{L}\\d ]+$",
            message = "symbols are not allowed"
    )
    private String name;

    private Long userId;

    @NotNull(message = "Category id cannot be null")
    @Positive(message = "{category_id_positive_message}")
    @EntityExistById(message = "{category_does't_exist}", entityType = EntityType.CATEGORIES)
    private Long categoryId;

    private String categoryName;

    @PositiveOrZero(message = "{non_negative_message}")
    private double salary;

    private Boolean isActive;
    private String created;
    private String updated;

    private List<@Valid EducationalInfoDto> educationInfoDtos;
    private List<@Valid WorkExperienceInfoDto> workExperienceInfoDtos;
    private List<@Valid ContactInfoDto> contactInfos;
}