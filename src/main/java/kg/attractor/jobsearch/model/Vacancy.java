package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Vacancy {
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
