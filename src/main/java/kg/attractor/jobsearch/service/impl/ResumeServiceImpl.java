package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.EducationInfoDao;
import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dao.WorkExperienceDao;
import kg.attractor.jobsearch.dto.UpdateResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.EducationInfoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UpdateResumeMapper;
import kg.attractor.jobsearch.dto.mapper.impl.WorkExperienceInfoMapper;
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
import java.util.Optional;

import static kg.attractor.jobsearch.util.validater.Validator.isValidResume;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<UpdateResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryService categoryService;
    private final UserDao userDao;
    private final UserService userService;
    private final UpdateResumeMapper resumeMapper;
    private final EducationInfoDao educationInfoDao;
    private final EducationInfoMapper educationInfoMapperDto;
    private final WorkExperienceDao workExperienceDao;
    private final WorkExperienceInfoMapper workExperienceInfoMapperDto;

    @Override
    public List<UpdateResumeDto> findAllResumes() {
        return resumeDao.findAllResumes().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public UpdateResumeDto findResumeById(Long id) {
        return resumeDao.findResumeById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException("Resume by id is not found " + id));
    }

    @Override
    public List<UpdateResumeDto> findResumesByCategory(Category resumeCategory) {
        if (Validator.isNotValid(resumeCategory))
            throw new IllegalArgumentException("resume category or category id is null");

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory.getId());

        return optionalResume.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public UpdateResumeDto updateResume(UpdateResumeDto resumeDto) {
        checkCategoryAndParams(resumeDto);

        Optional<Long> optionalId = resumeDao.updateResume(mapper.mapToEntity(resumeDto));

        return findAndMapToResumeOrThrowResumeNotFoundException(optionalId);
    }

    private UpdateResumeDto findAndMapToResumeOrThrowResumeNotFoundException(Optional<Long> optionalId) {
        return optionalId.flatMap(resumeDao::findResumeById).map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException("resume not found"));
    }

    private void checkCategoryAndParams(UpdateResumeDto resumeDto) {
        if (!isValidResume(resumeDto))
            throw new IllegalArgumentException("resume dto invalid");

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());
        boolean isUserExist = userService.checkIfUserExistById(resumeDto.getUserId());

        if (!isCategoryExist || !isUserExist)
            throw new IllegalArgumentException("category does not exist");
    }

    @Override
    public boolean deleteResume(Long resumeId) {
        return resumeDao.deleteResumeById(resumeId);
    }

    @Override
    public List<UpdateResumeDto> findUserCreatedResumes(String userEmail) {
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
