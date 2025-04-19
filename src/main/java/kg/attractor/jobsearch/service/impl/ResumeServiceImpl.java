package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Category;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.ResumeRepository;
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
    private final ResumeRepository resumeRepository;
    private final CategoryServiceImpl categoryService;
    private final UserService userService;
    private final ResumeMapper resumeMapper;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeRepository.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public ResumeDto findResumeById(Long id) {
        Validator.isValidId(id);

        return resumeRepository.findById(id)
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
    public List<ResumeDto> findResumesByCategoryId(Long categoryId) {
        Validator.isValidId(categoryId);

        boolean isCategoryExists = categoryService.checkIfCategoryExistsById(categoryId);

        if (!isCategoryExists)
            throw new CustomIllegalArgException(
                    "Category is not exists",
                    CustomBindingResult.builder()
                            .className(Category.class.getCanonicalName())
                            .fieldName("categoryId")
                            .rejectedValue(categoryId)
                            .build()
            );

        var optionalResume = resumeRepository.findResumesByCategoryId(categoryId);

        return optionalResume.stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public Long updateResume(ResumeDto resumeDto) {
        return resumeRepository.save(mapper.mapToEntity(resumeDto)).getId();
    }

    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {
        Resume resume = resumeMapper.mapToEntity(resumeDto);
        return resumeMapper.mapToDto(resumeRepository.save(resume));
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

        resumeRepository.deleteById(resumeId);
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

        return resumeRepository.findResumeByUserId(getUser.getUserId()).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeRepository.findById(resumeId).isPresent();
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes() {
        Long userId = authorizedUserService.getAuthorizedUserId();

        return resumeRepository.findResumeByUserId(userId).stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public List<Long> findAllResumesIds() {
        return resumeRepository.findAll().stream()
                .map(Resume::getId)
                .toList();
    }
}
