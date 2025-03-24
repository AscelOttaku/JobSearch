package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    ResumeDto findResumeById(Long id);

    List<ResumeDto> findResumesByCategory(Category category);

    boolean updateResume(ResumeDto resumeDto, Long resumeId);

    Long createResume(Resume resume);

    void checkCategoryAndParams(ResumeDto resumeDto);

    boolean deleteResume(Long resumeId);

    List<ResumeDto> findUserCreatedResumes(String userEmail);

    boolean isResumeExist(Long resumeId);
}
