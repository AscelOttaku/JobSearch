package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.dto.mapper.impl.ResumeMapper;
import kg.attractor.jobsearch.enums.Roles;
import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.repository.ResumeRepository;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final Mapper<ResumeDto, Resume> mapper;
    private final ResumeRepository resumeRepository;
    private final CategoryServiceImpl categoryService;
    private final ResumeMapper resumeMapper;
    private final AuthorizedUserService authorizedUserService;
    private final ContactTypeService contactTypeService;
    private final PageHolderWrapper pageHolderWrapper;

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
                if (phoneNumberType.equals("PHONE_NUMBER") && contactInfoDto.getContactValue() == null)
                    contactInfoDto.setContactValue("+996");
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
    public void deleteResumeById(Long resumeId) {
        Validator.isValidId(resumeId);

        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new NoSuchElementException("Resume not found by id " + resumeId));

        if (!Objects.equals(resume.getUser().getUserId(), authorizedUserService.getAuthorizedUser().getUserId()))
            throw new IllegalArgumentException("Resume doesn't belongs to authorized user");

        resumeRepository.deleteById(resumeId);
    }

    @Override
    public PageHolder<ResumeDto> findUserCreatedActiveResumes(int page, int size) {
        var authUser = authorizedUserService.getAuthorizedUser();

        if (!authUser.getAccountType().equals(Roles.JOB_SEEKER.getValue()))
            throw new IllegalArgumentException("User account type is not valid");

        Page<Resume> activeResumesByUserId = resumeRepository.findActiveResumesByUserId(authUser.getUserId(), PageRequest.of(page, size));

        return pageHolderWrapper.wrapPageHolderResumes(activeResumesByUserId);
    }

    @Override
    public boolean isResumeExist(Long resumeId) {
        return resumeRepository.findById(resumeId).isPresent();
    }

    @Override
    public PageHolder<ResumeDto> findUserCreatedResumes(int page, int size) {
        var authUser = authorizedUserService.getAuthorizedUser();

        if (!authUser.getAccountType().equalsIgnoreCase("job_seeker"))
            throw new IllegalArgumentException("User account type is not valid");

        Page<Resume> resumesPage = resumeRepository.findResumeByUserId(authUser.getUserId(), PageRequest.of(page, size));

        return pageHolderWrapper.wrapPageHolderResumes(resumesPage);
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

        return pageHolderWrapper.wrapPageHolderResumes(resumePage);
    }

    @Override
    public List<ResumeDto> findAllResumesByRespondIdAndVacancyId(Long respondId, Long vacancyId) {
        Validator.isValidId(respondId);
        Validator.isValidId(vacancyId);

        return resumeRepository.findAlLResumesByRespondIdAndVacancyId(respondId, vacancyId)
                .stream()
                .map(resumeMapper::mapToDto)
                .collect(Collectors.toList());
    }
}
