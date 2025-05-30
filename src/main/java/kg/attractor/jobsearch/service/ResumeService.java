package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeService {
    List<ResumeDto> findAllResumes();

    ResumeDto findPreparedResumeById(Long id);

    ResumeDto findResumeById(Long resumeId);

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

    PageHolder<ResumeDto> findRespondedToVacancyResumes(Long vacancyId, int page, int size);

    ResumeDto findResumeByRespondId(Long respondId);

    String findResumeNameByRespondId(Long respondId);

    PageHolder<ResumeDto> findAllResumesByCategoryName(String categoryName, int page, int size);
}
