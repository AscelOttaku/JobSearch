package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.jobsearch.util.marks.UpdateOn;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EducationalInfoDto {

    @NotNull(message = "{null_message}", groups = UpdateOn.class)
    @Positive(message = "{positive_number_message}", groups = UpdateOn.class)
    private Long id;

    private String institution;
    private String program;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String degree;
}
