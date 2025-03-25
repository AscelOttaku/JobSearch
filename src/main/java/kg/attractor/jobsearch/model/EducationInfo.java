package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class EducationInfo {
    private Long id;
    private Long resumeId;
    private String institution;
    private String program;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String degree;
}
