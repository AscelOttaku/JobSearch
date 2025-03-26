package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;

public interface ResumeDetailedInfoService {
    ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto);

    void updateResumeDetailedInfo(Long resumeId, ResumeDetailedInfoDto resumeDetailedInfoDto);
}
