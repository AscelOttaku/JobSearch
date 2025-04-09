package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDetailedInfoDto;

import java.util.List;

public interface ResumeDetailedInfoService {
    ResumeDetailedInfoDto createResume(ResumeDetailedInfoDto resumeDetailedInfoDto);

    void updateResumeDetailedInfo(ResumeDetailedInfoDto resumeDetailedInfoDto, Long resumeId);

    List<ResumeDetailedInfoDto> findAllResumesWithDetailedInfo();
}
