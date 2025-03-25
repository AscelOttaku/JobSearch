package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class UpdatedEducationalInfoDto {
    private Long id;
    private String institution;
    private String program;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String degree;
}
