package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class WorkExperienceInfoDto {
    private Long id;

    @PositiveOrZero(message = "Year cannot be negative")
    private Integer years;

    @NotBlank(message = "{blank_message}")
    private String companyName;

    @NotBlank(message = "{blank_message}")
    private String position;

    @NotBlank(message = "{blank_message}")
    private String responsibilities;
}
