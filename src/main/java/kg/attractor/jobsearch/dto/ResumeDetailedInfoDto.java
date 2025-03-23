package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResumeDetailedInfoDto {
    private CreateResumeDto resumeDto;
    private EducationInfoDto educationInfoDto;
    private WorkExperienceInfoDto workExperienceInfoDto;
}
