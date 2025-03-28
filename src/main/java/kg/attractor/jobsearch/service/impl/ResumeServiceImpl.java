package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dao.UserDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.util.validater.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryServiceImpl categoryService;
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
        Validator.isValidId(id);

        return resumeDao.findResumeById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException(
                        "Resume by id is not found ",
                        CustomBindingResult.builder()
                                .className(Resume.class.getCanonicalName())
                                .fieldName("id")
                                .rejectedValue(id)
                                .build()
                ));
    }

    @Override
    public List<ResumeDto> findResumesByCategory(Long resumeCategory) {
        Validator.isValidId(resumeCategory);

        var optionalResume = resumeDao.findResumesByCategory(resumeCategory);

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
        Validator.isValidId(resumeDto.getCategoryId());
        Validator.isValidId(resumeDto.getUserId());

        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());
        boolean jobSeekerId = userService.checkIfJobSeekerExistById(resumeDto.getUserId());

        if (!isCategoryExist)
            throw new CustomIllegalArgException(
                    "category doesn't exist",
                    CustomBindingResult.builder()
                            .className(Category.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(resumeDto.getCategoryId())
                            .build()
            );

        if (!jobSeekerId)
            throw new CustomIllegalArgException(
                    "jobSeeker don't exist",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(resumeDto.getUserId())
                            .build()
            );
    }

    @Override
    public void checkUpdateResumeParams(ResumeDto resumeDto) {
        boolean isCategoryExist = categoryService.checkIfCategoryExistsById(resumeDto.getCategoryId());

        if (!isCategoryExist)
            throw new CustomIllegalArgException(
                    "category is not exist",
                    CustomBindingResult.builder()
                            .className(Category.class.getSimpleName())
                            .fieldName("id")
                            .rejectedValue(resumeDto.getCategoryId())
                            .build()
            );
    }

    @Override
    public void deleteResume(Long resumeId) {
        Validator.isValidId(resumeId);

        boolean res = resumeDao.deleteResumeById(resumeId);

        if (!res)
            throw new ResumeNotFoundException(
                    "Resume by id is not found " + resumeId,
                    CustomBindingResult.builder()
                            .className(Resume.class.getCanonicalName())
                            .fieldName("id")
                            .rejectedValue(resumeId)
                            .build()
            );
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes(String userEmail) {
        var optionalUser = userDao.findUserByEmail(userEmail);

        User user = optionalUser.orElseThrow(() ->
                new UserNotFoundException(
                        "User not found by email " + userEmail,
                        CustomBindingResult.builder()
                                .className(User.class.getCanonicalName())
                                .fieldName("email")
                                .rejectedValue(userEmail)
                                .build()
                ));

        if (!user.getAccountType().equalsIgnoreCase("jobSeeker"))
            throw new CustomIllegalArgException(
                    "User account type is not valid",
                    CustomBindingResult.builder()
                            .className(User.class.getCanonicalName())
                            .fieldName("accountType")
                            .rejectedValue(user.getAccountType())
                            .build()
            );

        return resumeDao.findUserCreatedResumes(user.getUserId()).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeDao.findResumeById(resumeId).isPresent();
    }

    @Override
    public List<ResumeDto> findResumeByUserId(Long userId) {
        return resumeDao.findResumeByUserId(userId).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public List<Long> findAllResumesIds() {
        return resumeDao.findAllResumes().stream()
                .map(Resume::getId)
                .toList();
    }
}
