package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.util.EntityType;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{min_experience_negative_message}")
    @Max(value = 5, message = "{max_experience_message}")
    private Integer expFrom;

    @NotNull(message = "{null_message}")
    @PositiveOrZero(message = "{max_experience_negative_message}")
    @Max(value = 10, message = "{max_experience_message}")
    private Integer expTo;

    private boolean isActive;

    private Long userId;

    private String created;
    private String updated;

    @AssertTrue(message = "expTo should be bigger")
    private boolean isExpToBiggerExpFrom() {
        return expTo.compareTo(expFrom) > 0;
    }
}