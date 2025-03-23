package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WorkExperienceInfoDto {
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
