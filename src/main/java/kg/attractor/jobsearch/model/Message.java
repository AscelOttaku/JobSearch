package kg.attractor.jobsearch.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class Message {
    private RespondedApplications respondedApplications;
    private String content;
    private LocalDateTime time;

    @Autowired
    public Message(RespondedApplications respondedApplications) {
        this.respondedApplications = respondedApplications;
    }
}
