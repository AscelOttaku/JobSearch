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

    @NotNull(message = "years cannot be null")
    @PositiveOrZero(message = "Year cannot be negative")
    @Max(value = 10, message = "max year is 10")
    private Integer years;

    @NotBlank(message = "{blank_message}")
    private String companyName;

    @NotBlank(message = "{blank_message}")
    private String position;

    @NotBlank(message = "{blank_message}")
    private String responsibilities;
}
