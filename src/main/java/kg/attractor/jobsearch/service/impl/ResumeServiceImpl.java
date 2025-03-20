package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import static kg.attractor.jobsearch.util.validater.Validator.isValidResume;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> resumeMapper;
    private final ResumeDao resumeDao;
    private final UserDao userDao;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeDao.findAllResumes().stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public List<ResumeDto> findResumesByCategory(Category resumeCategory) {
        if (Validator.isNotValid(resumeCategory))
            throw new IllegalArgumentException("resume category or category id is null");

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory.getId());

        return optionalResume.stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public Long createResume(ResumeDto resumeDto) {
        if (!isValidResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        return resumeDao.createResume(resumeMapper.mapToEntity(resumeDto))
                .orElse(-1L);
    }

    @Override
    public Long updateResume(ResumeDto resumeDto) {
        if (!isValidResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        return resumeDao.updateResume(resumeMapper.mapToEntity(resumeDto))
                .orElse(-1L);
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        return resumeDao.deleteResumeById(resumeId);
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes(UserDto userDto) {
        if (Validator.isNotValidUser(userDto))
            throw new IllegalArgumentException("User account type is not jobSeeker");

        var optionalUser = userDao.findUserByEmail(userDto.getEmail());

        return optionalUser.map(user ->
                resumeDao.findUserCreatedResumes(user.getId()).stream()
                        .map(resumeMapper::mapToDto)
                        .toList()).orElse(Collections.emptyList());
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeDao.findResumeById(resumeId).isPresent();
    }
}
