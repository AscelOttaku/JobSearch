package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.User;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    List<ResumeDto> findResumesByCategory(Category category);

    Long createResume(ResumeDto resumeDto);

    Long updateResume(ResumeDto resumeDto);

    boolean deleteResume(Long resumeId);

    List<ResumeDto> findUserCreatedResumes(User user);
}
