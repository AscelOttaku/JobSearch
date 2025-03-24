package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UpdateWorkExperienceInfoDto {
    private Long id;
    private Integer years;
    private String companyName;
    private String position;
    private String responsibilities;
}
