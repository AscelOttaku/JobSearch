package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class CreateEducationInfoDto {
    private String institution;
    private String program;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String degree;
}
