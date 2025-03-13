package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Resume {
    private Long id;
    private Long userId;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDate created;
    private LocalDate updated;
}
