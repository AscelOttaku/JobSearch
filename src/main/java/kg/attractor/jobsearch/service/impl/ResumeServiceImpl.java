package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeRepository resumeRepository;
    private final CategoryServiceImpl categoryService;
    private final UserService userService;
    private final ResumeMapper resumeMapper;
    private final AuthorizedUserService authorizedUserService;
    private final ContactTypeService contactTypeService;

    @Override
    public List<ResumeDto> findAllResumes() {
        return resumeRepository.findAll().stream()
                .map(mapper::mapToDto)
                .toList();
    }

    @Override
    public ResumeDto findResumeById(Long id) {
        Validator.isValidId(id);

        ResumeDto resumeDto = resumeRepository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResumeNotFoundException(
                        "Resume by id is not found ",
                        CustomBindingResult.builder()
                                .className(Resume.class.getCanonicalName())
                                .fieldName("id")
                                .rejectedValue(id)
                                .build()
                ));

        if (resumeDto.getWorkExperienceInfoDtos().isEmpty())
            resumeDto.setWorkExperienceInfoDtos(List.of(new WorkExperienceInfoDto()));

        if (resumeDto.getEducationInfoDtos().isEmpty())
            resumeDto.setEducationInfoDtos(List.of(new EducationalInfoDto()));

        if (resumeDto.getContactInfos().size() < 5) {
            String resumesContactTypes = resumeDto.getContactInfos()
                    .stream()
                    .map(contactInfoDto -> contactInfoDto.getContactType().getType())
                    .collect(Collectors.joining(", "));

            List<ContactInfoDto> contactInfos = contactTypeService.findAllContactTypes()
                    .stream()
                    .filter(contactType -> !resumesContactTypes.contains(contactType.getType()))
                    .map(contactTypeDto -> ContactInfoDto.builder()
                            .contactType(contactTypeDto)
                            .build())
                    .toList();

            contactInfos.forEach(contactInfoDto -> {
                String phoneNumberType = contactInfoDto.getContactType().getType();
                if (phoneNumberType.equals("PHONE_NUMBER") && contactInfoDto.getValue() == null)
                    contactInfoDto.setValue("+996");
            });

            resumeDto.getContactInfos().addAll(contactInfos);
        }

        return resumeDto;
    }


    @Override
    public List<ResumeDto> findResumesByCategoryId(Long categoryId) {
        Validator.isValidId(categoryId);
        categoryService.findCategoryById(categoryId);

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
    public List<ResumeDto> findUserCreatedResumes(String userEmail, int page, int size) {
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

        return resumeRepository.findResumeByUserId(getUser.getUserId(), PageRequest.of(page, size))
                .stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeRepository.findById(resumeId).isPresent();
    }

    @Override
    public PageHolder<ResumeDto> findUserCreatedResumes(int page, int size) {
        Long userId = authorizedUserService.getAuthorizedUserId();

        Page<Resume> resumesPage = resumeRepository.findResumeByUserId(userId, PageRequest.of(page, size));

        return PageHolder.<ResumeDto>builder()
                .content(resumesPage.stream()
                        .map(resumeMapper::mapToDto)
                        .toList())
                .page(page)
                .size(size)
                .totalPages(resumesPage.getTotalPages())
                .hasNextPage(resumesPage.hasNext())
                .hasPreviousPage(resumesPage.hasPrevious())
                .build();
    }

    @Override
    public List<ResumeDto> findUserCreatedResumes() {
        return resumeRepository.findResumeByUserId(authorizedUserService.getAuthorizedUserId())
                .stream()
                .map(resumeMapper::mapToDto)
                .toList();
    }

    @Override
    public List<Long> findAllResumesIds() {
        return resumeRepository.findAll().stream()
                .map(Resume::getId)
                .toList();
    }

    @Override
    public PageHolder<ResumeDto> findAllResumes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Resume> resumePage = resumeRepository.findAllResumes(pageable);

        return PageHolder.<ResumeDto>builder()
                .content(resumePage.stream()
                        .map(resumeMapper::mapToDto)
                        .toList())
                .page(page)
                .size(resumePage.getSize())
                .totalPages(resumePage.getTotalPages())
                .hasNextPage(resumePage.hasNext())
                .hasPreviousPage(resumePage.hasPrevious())
                .build();
    }
}
