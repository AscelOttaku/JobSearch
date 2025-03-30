package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResumeDto {
    private Long id;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 3, max = 40, message = "{3_30_size_message}")
    @Pattern(
            regexp = "^[a-zA-Zа-яА-ЯёЁ ]+$",
            message = "{resume_name}"
    )
    private String name;

    @NotNull(message = "{null_message}")
    @Positive(message = "{user_id_positive_message}")
    private Long userId;

    @NotNull(message = "{null_message}")
    @Positive(message = "{category_id_positive_message}")
    private Long categoryId;

    @PositiveOrZero(message = "{non_negative_message}")
    private double salary;

    private boolean isActive;
}