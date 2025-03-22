package kg.attractor.jobsearch.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id"})
public class RespondedApplication {
    private Long id;
    private Long resumeId;
    private Long vacancyId;
    private Boolean confirmation;
}
