package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class VacancyDto {

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 30, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^\\p{L}+$",
            message = "{symbol_numbers_pattern_message}"
    )
    private String name;

    private String description;

    @NotNull(message = "{null_message}")
    @Positive(message = "{positive_number_message}")
    private Long categoryId;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{non_negative_message}")
    private Double salary;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{min_experience_negative_message}")
    @Max(value = 90, message = "{max_experience_message}")
    private Integer expFrom;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{max_experience_negative_message}")
    @Max(value = 90, message = "{max_experience_message}")
    private Integer expTo;

    private boolean isActive;
    private Long userId;
}