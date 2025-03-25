package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    ResumeDto findResumeById(Long id);

    List<ResumeDto> findResumesByCategory(Long categoryId);

    boolean updateResume(ResumeDto resumeDto, Long resumeId);

    Long createResume(ResumeDto resumeDto);

    void checkCreateResumeParams(ResumeDto resumeDto);

    void checkUpdateResumeParams(ResumeDto resumeDto);

    boolean deleteResume(Long resumeId);

    List<ResumeDto> findUserCreatedResumes(String userEmail);

    boolean isResumeExist(Long resumeId);
}
