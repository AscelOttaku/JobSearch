package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.*;
import kg.attractor.jobsearch.annotations.EntityExistById;
import kg.attractor.jobsearch.util.EntityType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    private Long userId;

    @NotNull(message = "{null_message}")
    @Positive(message = "{category_id_positive_message}")
    @EntityExistById(message = "{category_does't_exist}", entityType = EntityType.CATEGORIES)
    private Long categoryId;

    private String categoryName;

    @PositiveOrZero(message = "{non_negative_message}")
    private double salary;

    private Boolean isActive;
    private String created;
    private String updated;
}