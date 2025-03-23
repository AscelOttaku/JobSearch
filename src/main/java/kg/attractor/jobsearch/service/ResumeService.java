package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UpdateResumeDto;
import kg.attractor.jobsearch.model.Category;

import java.util.List;

public interface ResumeService {
    List<UpdateResumeDto> findAllResumes();

    UpdateResumeDto findResumeById(Long id);

    List<UpdateResumeDto> findResumesByCategory(Category category);

    UpdateResumeDto updateResume(UpdateResumeDto resumeDto);

    boolean deleteResume(Long resumeId);

    List<UpdateResumeDto> findUserCreatedResumes(String userEmail);

    boolean isResumeExist(Long resumeId);
}
