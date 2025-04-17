package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RespondApplicationDto {

    @NotNull(message = "{null_message}")
    @Positive(message = "{positive_number_message}")
    private ResumeDto resumeDto;

    @NotNull(message = "{null_message}")
    @Positive(message = "{positive_number_message}")
    private VacancyDto vacancyDto;

    private boolean confirmation;
}