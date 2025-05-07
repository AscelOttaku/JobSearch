package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.IsExpFromAndExpToCorrectFormat;
import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@IsExpFromAndExpToCorrectFormat
public class VacancyDto {
    private Long vacancyId;

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
    private Long categoryId;

    private String categoryName;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{non_negative_message}")
    private Double salary;

    @PositiveOrZero(message = "{min_experience_negative_message}")
    @Max(value = 5, message = "{value_max_size_should_be_equals_5}")
    private Integer expFrom;

    @PositiveOrZero(message = "{max_experience_negative_message}")
    @Max(value = 10, message = "{value_max_size_should_be_equals_10}")
    private Integer expTo;

    private boolean isActive;

    private UserDto user;

    private String created;
    private String updated;

    private List<RespondApplicationDto> respondedApplications;
}