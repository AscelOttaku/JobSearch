package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceInfoDto {
    private Long id;

    private Long resumeId;

    @NotNull(message = "{years_cannot_be_null}")
    @PositiveOrZero(message = "{non_negative_message}")
    @Max(value = 10, message = "{max_year_10}")
    private Integer years;

    @NotBlank(message = "{blank_message}")
    private String companyName;

    @NotBlank(message = "{blank_message}")
    private String position;

    @NotBlank(message = "{blank_message}")
    private String responsibilities;
}
