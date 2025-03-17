package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;
import java.util.Optional;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    Optional<ResumeDto> findResumeByCategory(String resumeCategory);

    Long createResume(ResumeDto resumeDto);

    Long updateResume(ResumeDto resumeDto);

    boolean deleteResume(Long resumeId);
}
