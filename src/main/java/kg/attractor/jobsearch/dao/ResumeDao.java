package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;

import java.util.List;

public interface ResumeDao {
    List<Resume> findResumesByCategory(Category category);

    List<Resume> findUserCreatedResumes(Long userId);
}
