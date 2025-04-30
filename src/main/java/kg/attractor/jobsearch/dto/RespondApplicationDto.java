package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.jobsearch.annotations.ValidateRespondNotExist;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@ValidateRespondNotExist
public class RespondApplicationDto {

    @NotNull(message = "{null_message}")
    @Positive(message = "{positive_number_message}")
    private Long resumeId;

    @NotNull(message = "{null_message}")
    private Long vacancyId;

    private Boolean confirmation;
}