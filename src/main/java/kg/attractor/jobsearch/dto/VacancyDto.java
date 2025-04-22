package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.annotations.IsExpFromAndExpToCorrectFormat;
import kg.attractor.jobsearch.enums.EntityType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IsExpFromAndExpToCorrectFormat
public class VacancyDto {
    private Long vacancyId;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^[\\p{L}\\s\\d]+$",
            message = "{symbol_numbers_pattern_message}"
    )
    private String name;

    private String description;

    @NotNull(message = "{null_message}")
    @Positive(message = "{positive_number_message}")
    @EntityExistById(message = "{category_does't_exist}", entityType = EntityType.CATEGORIES)
    private Long categoryId;

    private String categoryName;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{non_negative_message}")
    private Double salary;

    @PositiveOrZero(message = "{min_experience_negative_message}")
    @Max(value = 5, message = "value max size should be equals 5")
    private Integer expFrom;

    @PositiveOrZero(message = "{max_experience_negative_message}")
    @Max(value = 10, message = "value max size should be equals 10")
    private Integer expTo;

    private boolean isActive;

    private UserDto user;

    private String created;
    private String updated;
}