package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown = true)
public class ResumeDto {
    private String name;
    private Long userId;
    private Long categoryId;
    private double salary;
    private boolean isActive;
}
