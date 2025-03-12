package kg.attractor.jobsearch.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Vacancy {
    private String name;
    private String description;
    private double salary;
    private int exp_from;
    private int exp_to;
    private boolean isActive;
    private User user;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Autowired
    public Vacancy(User user) {
        this.user = user;
    }
}
