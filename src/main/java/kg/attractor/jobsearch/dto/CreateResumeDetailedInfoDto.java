package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class CreateResumeDetailedInfoDto {
    private ResumeDto resumeDto;
    private List<CreateEducationInfoDto> educationInfoDtos;
    private List<CreateWorkExperienceInfoDto> workExperienceInfoDtos;
}
