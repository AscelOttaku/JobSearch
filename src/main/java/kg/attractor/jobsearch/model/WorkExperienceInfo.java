package kg.attractor.jobsearch.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkExperienceInfo {
    private Resume resume;
    private int years;
    private String companyName;
    private String position;
    private String responsibilities;

    @Autowired
    public WorkExperienceInfo(Resume resume) {
        this.resume = resume;
    }
}
