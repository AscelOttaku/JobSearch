package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.service.ResumeService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ResumeServiceImpl implements ResumeService {

    @Override
    public List<ResumeDto> findAllResumes() {
        //ToDO implement find all logic

        return Collections.emptyList();
    }

    @Override
    public Optional<ResumeDto> findResumeByCategory(String resumeCategory) {
        //ToDo find resume by category logic

        return Optional.empty();
    }

    @Override
    public boolean createResume(ResumeDto resumeDto) {
        //ToDO create resume logic

        return true;
    }

    @Override
    public boolean updateResume(ResumeDto resumeDto) {
        //ToDo update resume logic

        return true;
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        //ToDo delete resume by id

        return true;
    }
}
