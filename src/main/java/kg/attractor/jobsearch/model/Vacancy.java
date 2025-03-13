package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Vacancy {
    private Long id;
    private String name;
    private String description;
    private double salary;
    private int expFrom;
    private int expTo;
    private boolean isActive;
    private Long userId;
    private LocalDateTime created;
    private LocalDateTime updated;

}
