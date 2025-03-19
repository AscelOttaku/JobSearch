package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ResumeDto {
    private Long id;
    private Long userId;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
}
