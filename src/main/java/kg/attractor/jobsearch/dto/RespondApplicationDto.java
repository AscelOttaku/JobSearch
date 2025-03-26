package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import kg.attractor.jobsearch.util.marks.CreateOn;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RespondApplicationDto {

    @NotNull(message = "{null_message}", groups = CreateOn.class)
    @Positive(message = "{positive_number_message}", groups = CreateOn.class)
    private Long resumeId;

    @NotNull(message = "{null_message}", groups = CreateOn.class)
    @Positive(message = "{positive_number_message}", groups = CreateOn.class)
    private Long vacancyId;

    private boolean confirmation;
}