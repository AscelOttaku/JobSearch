package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class VacancyDto {
    private Long id;
    private String name;
    private String description;
    private double salary;
    private Integer expFrom;
    private Integer expTo;
    private boolean isActive;
    private Long userId;
    private LocalDate created;
    private LocalDate updated;
}
