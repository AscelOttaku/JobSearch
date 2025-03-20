package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ResumeDao {
    List<Resume> findResumesByCategory(Long categoryId);

    List<Resume> findUserCreatedResumes(Long userId);

    List<Resume> findAllResumes();
}
