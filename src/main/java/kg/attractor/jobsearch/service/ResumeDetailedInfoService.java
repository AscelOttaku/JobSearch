package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.Map;

public interface ResumeDetailedInfoService {
    Long createResume(ResumeDto resumeDto);

    void updateResumeDetailedInfo(ResumeDto resumeDto, Long resumeId);

    Map<String, Object> getResumeDtoModel();
}
