package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Resume {
    private Long id;
    private User user;
    private String name;
    private Long categoryId;
    private double salary;
    private boolean isActive;
    private LocalDateTime created;
    private LocalDateTime updated;
}
