package kg.attractor.jobsearch.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EducationInfo {
    private Resume resume;
    private String institution;
    private String program;
    private LocalDate startDate;
    private LocalDate endDate;
    private String degree;

    @Autowired
    public EducationInfo(Resume resume) {
        this.resume = resume;
    }
}
