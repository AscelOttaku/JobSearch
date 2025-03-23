package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UpdateResumeDto {
    private Long id;
    private String name;
    private Long userId;
    private Long categoryId;
    private double salary;
    private boolean isActive;
}
