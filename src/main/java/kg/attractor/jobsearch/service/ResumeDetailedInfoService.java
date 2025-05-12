package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

public interface ResumeDetailedInfoService {
    Long createResume(ResumeDto resumeDto);

    void updateResumeDetailedInfo(ResumeDto resumeDto);

    ResumeDto getResumeDtoModel();
}
