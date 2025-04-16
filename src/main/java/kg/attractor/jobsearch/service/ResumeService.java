package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    ResumeDto findResumeById(Long id);

    List<ResumeDto> findResumesByCategory(Long categoryId);

    void updateResume(ResumeDto resumeDto);

    ResumeDto createResume(ResumeDto resumeDto);

    void deleteResume(Long resumeId);

    List<ResumeDto> findUserCreatedResumes(String userEmail);

    boolean isResumeExist(Long resumeId);

    List<ResumeDto> findUserCreatedResumes();

    List<Long> findAllResumesIds();
}
