package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ResumeDto {
    private String name;
    private Long userId;
    private Long categoryId;
    private double salary;
    private boolean isActive;
}
