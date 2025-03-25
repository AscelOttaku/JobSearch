package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.CreateResumeDetailedInfoDto;
import kg.attractor.jobsearch.dto.UpdateResumeDetailedInfoDto;

public interface ResumeDetailedInfoService {
    CreateResumeDetailedInfoDto createResume(CreateResumeDetailedInfoDto resumeDetailedInfoDto);

    void updateResumeDetailedInfo(Long resumeId, UpdateResumeDetailedInfoDto resumeDetailedInfoDto);
}
