package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> resumeMapper;

    @Autowired
    public ResumeServiceImpl(Mapper<ResumeDto, Resume> resumeMapper) {
        this.resumeMapper = resumeMapper;
    }

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
    public Long createResume(ResumeDto resumeDto) {
        //ToDO create resume logic
        //return id of created object

        return -1L;
    }

    @Override
    public Long updateResume(ResumeDto resumeDto) {
        //ToDo update resume logic
        //return id of created object

        return -1L;
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        //ToDo delete resume by id
        //return id of created object

        return true;
    }
}
