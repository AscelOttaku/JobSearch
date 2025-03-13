package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EducationInfo {
    private Long id;
    private Long resumeId;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;
}
