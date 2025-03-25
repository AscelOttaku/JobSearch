package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class UpdateResumeDetailedInfoDto {
    private ResumeDto resumeDto;
    private List<UpdatedEducationalInfoDto> educationInfoDtos;
    private List<UpdateWorkExperienceInfoDto> workExperienceInfoDtos;
}
