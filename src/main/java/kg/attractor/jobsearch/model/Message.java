package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Message {
    private Long id;
    private Long respondedApplicationId;
    private String content;
    private LocalDateTime time;
}
