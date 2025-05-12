package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class WorkExperienceInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

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
