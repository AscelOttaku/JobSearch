package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreateResumeDto {
    private String name;
    private Long userId;
    private Long categoryId;
    private double salary;
    private boolean isActive;
}
