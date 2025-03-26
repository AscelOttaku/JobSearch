package kg.attractor.jobsearch.dto;

import jakarta.validation.Valid;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
public class ResumeDetailedInfoDto {

    @Valid
    private ResumeDto resumeDto;

    private List<EducationalInfoDto> educationInfoDtos;
    private List<WorkExperienceInfoDto> workExperienceInfoDtos;
}
