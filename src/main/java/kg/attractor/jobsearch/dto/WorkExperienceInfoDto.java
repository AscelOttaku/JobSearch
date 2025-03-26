package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.jobsearch.util.marks.UpdateOn;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class WorkExperienceInfoDto {

    @NotNull(message = "{null_message}", groups = UpdateOn.class)
    @Positive(message = "{positive_number_message}", groups = UpdateOn.class)
    private Long id;

    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
