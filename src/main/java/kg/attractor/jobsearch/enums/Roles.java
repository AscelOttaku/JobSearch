package kg.attractor.jobsearch.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Roles {
    EMPLOYER("EMPLOYER"), JOB_SEEKER("JOB_SEEKER");

    private final String value;
}