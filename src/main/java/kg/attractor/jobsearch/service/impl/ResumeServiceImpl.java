package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static kg.attractor.jobsearch.util.validater.Validator.isValidCreateResume;
import static kg.attractor.jobsearch.util.validater.Validator.isValidUpdateResume;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final UserDao userDao;
    private final UserService userService;
    private final ResumeMapper resumeMapper;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeDao.findAllResumes().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public ResumeDto findResumeById(Long id) {
        return resumeDao.findResumeById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException("Resume by id is not found " + id));
    }

    @Override
    public List<ResumeDto> findResumesByCategory(Category resumeCategory) {
        if (Validator.isNotValid(resumeCategory))
            throw new IllegalArgumentException("resume category or category id is null");

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory.getId());

        return optionalResume.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public boolean updateResume(ResumeDto resumeDto, Long resumeId) {
        return resumeDao.updateResume(mapper.mapToEntity(resumeDto), resumeId);
    }

    @Override
    public Long createResume(ResumeDto resumeDto) {
        Resume resume = resumeMapper.mapToEntity(resumeDto);
        return resumeDao.create(resume).orElse(-1L);
    }

    @Override
    public void checkCreateResumeParams(ResumeDto resumeDto) {
        if (!isValidCreateResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());
        boolean jobSeekerId = userService.checkIfJobSeekerExistById(resumeDto.getUserId());

        if (!isCategoryExist || !jobSeekerId)
            throw new IllegalArgumentException("category or jobSeeker id is invalid");
    }

    @Override
    public void checkUpdateResumeParams(ResumeDto resumeDto) {
        if (!isValidUpdateResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());

        if (!isCategoryExist)
            throw new IllegalArgumentException("category or jobSeeker id is invalid");
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        return resumeDao.deleteResumeById(resumeId);
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes(String userEmail) {
        var optionalUser = userDao.findUserByEmail(userEmail);

        User user = optionalUser.orElseThrow(() ->
                new UserNotFoundException("User not found by email " + userEmail));

        if (!Validator.isValidUserAccountType(user))
            throw new IllegalArgumentException("User account type is not valid");

        return resumeDao.findUserCreatedResumes(user.getUserId()).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeDao.findResumeById(resumeId).isPresent();
    }
}
