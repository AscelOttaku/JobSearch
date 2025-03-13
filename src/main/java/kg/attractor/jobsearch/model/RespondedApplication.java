package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RespondedApplication {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private boolean confirmation;
}
