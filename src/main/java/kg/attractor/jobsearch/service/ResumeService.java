package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    ResumeDto findResumeById(Long id);

    List<ResumeDto> findResumesByCategoryId(Long categoryId);

    Long updateResume(ResumeDto resumeDto);

    ResumeDto createResume(ResumeDto resumeDto);

    void deleteResumeById(Long resumeId);

    PageHolder<ResumeDto> findUserCreatedActiveResumes(int page, int size);

    boolean isResumeExist(Long resumeId);

    PageHolder<ResumeDto> findUserCreatedResumes(int page, int size);

    List<ResumeDto> findUserCreatedResumes();

    List<Long> findAllResumesIds();

    PageHolder<ResumeDto> findAllResumes(int page, int size);

    List<ResumeDto> findAllResumesByRespondIdAndVacancyId(Long respondId, Long vacancyId);
}
