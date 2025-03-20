package kg.attractor.jobsearch.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactInfo {
    private Long id;
    private Long contactTypeId;
    private Long resumeId;
    private String value;
}
