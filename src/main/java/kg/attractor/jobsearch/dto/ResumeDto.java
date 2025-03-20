package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResumeDto {
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
}
