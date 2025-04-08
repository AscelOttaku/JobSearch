package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dao.ResumeDao;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeDao resumeDao;
    private final CategoryServiceImpl categoryService;
    private final UserService userService;
    private final ResumeMapper resumeMapper;
    private final AuthorizedUserService authorizedUserService;

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

        boolean isCategoryExists = categoryService.findCategoryById(resumeCategory).isPresent();

        if (!isCategoryExists)
            throw new CustomIllegalArgException(
                    "Category is not exists",
                    CustomBindingResult.builder()
                            .className(Category.class.getCanonicalName())
                            .fieldName("categoryId")
                            .rejectedValue(resumeCategory)
                            .build()
            );

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
    public void deleteResume(Long resumeId) {
        Validator.isValidId(resumeId);

        if (!Objects.equals(resumeId, authorizedUserService.getAuthorizedUser().getUserId()))
            throw new CustomIllegalArgException(
                    "Resume doesn't belongs to authorized user",
                    CustomBindingResult.builder()
                            .className(Resume.class.getSimpleName())
                            .fieldName("resumeId")
                            .rejectedValue(resumeId)
                            .build()
            );

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
        var getUser = userService.findUserByEmail(userEmail);

        if (!getUser.getAccountType().equalsIgnoreCase("jobSeeker"))
            throw new CustomIllegalArgException(
                    "User account type is not valid",
                    CustomBindingResult.builder()
                            .className(User.class.getCanonicalName())
                            .fieldName("accountType")
                            .rejectedValue(getUser.getAccountType())
                            .build()
            );

        return resumeDao.findUserCreatedResumes(getUser.getUserId()).stream()
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
