package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResumeDetailedInfoDto {
    private CreateResumeDto resumeDto;
    private List<EducationInfoDto> educationInfoDtos;
    private List<WorkExperienceInfoDto> workExperienceInfoDtos;
}
