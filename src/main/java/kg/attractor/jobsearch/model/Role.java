package kg.attractor.jobsearch.model;

import lombok.Getter;

@Getter
public enum Role {
    EMPLOYER("EMPLOYER"), JOB_SEEKER("JOB_SEEKER");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
