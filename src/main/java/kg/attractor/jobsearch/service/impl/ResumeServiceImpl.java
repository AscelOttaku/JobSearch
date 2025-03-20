package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> resumeMapper;
    private final ResumeDao resumeDao;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeDao.findAllResumes().stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findResumesByCategory(Category resumeCategory) {
        if (resumeCategory == null || resumeCategory.getId() == null)
            throw new IllegalArgumentException("resume category or category id is null");

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory.getId());

        return optionalResume.stream()
                .map(resumeMapper::mapToDto)
                .toList();
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

    @Override
    public List<ResumeDto> findUserCreatedResumes(User user) {
        if (user == null || user.getId() == null || !user.getAccountType().equalsIgnoreCase("jobSeeker"))
            throw new IllegalArgumentException("User account type is not jobSeeker");

        return resumeDao.findUserCreatedResumes(user.getId()).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }
}
