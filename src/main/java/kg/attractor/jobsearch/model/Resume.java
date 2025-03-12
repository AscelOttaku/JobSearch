package kg.attractor.jobsearch.model;

import kg.attractor.jobsearch.model.enums.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Resume {
    private User user;
    private String name;
    private Category category;
    private double salary;
    private boolean isActive;
    private LocalDateTime created;
    private LocalDateTime updated;

    @Autowired
    public Resume(User user) {
        this.user = user;
    }
}
