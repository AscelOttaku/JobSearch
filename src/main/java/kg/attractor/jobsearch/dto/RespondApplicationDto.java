package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RespondApplicationDto {
    private Long resumeId;
    private Long vacancyId;
    private boolean confirmation;
}
