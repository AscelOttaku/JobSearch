package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.dto.mapper.impl.RespondPageHolderWrapper;
import kg.attractor.jobsearch.dto.mapper.impl.VacancyMapper;
import kg.attractor.jobsearch.enums.Roles;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.repository.RespondedApplicationRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.annotations.validators.ValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespondServiceImpl implements RespondService {
    private final RespondedApplicationRepository respondedApplicationRepository;
    private final RespondApplicationMapper respondApplicationMapper;
    private final ResumeService resumeService;
    private final AuthorizedUserService authorizedUserService;
    private final VacancyService vacancyService;
    private final VacancyMapper vacancyMapper;

    @Transactional
    @Override
    public void createRespond(RespondApplicationDto respondApplicationDto) {
        UserDto userDto = authorizedUserService.getAuthorizedUser();

        if (!userDto.getAccountType().equals(Roles.JOB_SEEKER.getValue()))
            throw new IllegalArgumentException("User who's account type is not a job seeker cannot create a respond");

        ResumeDto resumeDto = resumeService.findPreparedResumeById(respondApplicationDto.getResumeId());
        vacancyService.findVacancyById(respondApplicationDto.getVacancyId());

        if (!resumeDto.getUserId().equals(userDto.getUserId()))
            throw new IllegalArgumentException("Resume doesn't belongs to authorized user");

        respondApplicationDto.setConfirmation(Boolean.FALSE);
        RespondedApplication respondedApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);

        respondedApplicationRepository.save(respondedApplication);
    }

    @Override
    public List<RespondApplicationDto> findAllActiveResponsesByUserId(Long userId) {
        ValidatorUtil.isValidId(userId);

        return respondedApplicationRepository.findRespondedApplicationsByEmployerId(userId)
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public List<RespondApplicationDto> findAllResponsesByEmployerId(Long userId) {
        ValidatorUtil.isValidId(userId);

        return respondedApplicationRepository.findRespondedApplicationsByEmployerId(userId)
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public List<RespondApplicationDto> findAllResponsesByJobSeekerId(Long userId) {
        ValidatorUtil.isValidId(userId);

        return respondedApplicationRepository.findAllRespondedApplicationsByJobSeekerId(
                authorizedUserService.getAuthorizedUserId()
        )
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public RespondApplicationDto findRespondById(Long respondId) {
        return respondedApplicationRepository.findById(respondId)
                .map(respondApplicationMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Respond not found by id " + respondId));
    }

    @Override
    public boolean validateRespondNotExist(RespondApplicationDto respondApplicationDto) {
        return respondedApplicationRepository.findRespondedApplicationByVacancyIdAndResumeId(
                        respondApplicationDto.getVacancyId(),
                        respondApplicationDto.getResumeId()
                )
                .isEmpty();
    }

    @Override
    public RespondPageHolder<VacancyDto, ResumeDto> findUserResponds(int page, int size) {
        Authentication authentication = authorizedUserService.getAuthentication();

        Page<RespondedApplication> respondApplicationDtos = respondedApplicationRepository
                .findAllRespondedApplicationsByUserEmail(
                        authentication.getName(),
                        PageRequest.of(page, size));

        Map<VacancyDto, Map<ResumeDto, Boolean>> pair = convertToMapKeyAsVacancyValueAsResume(respondApplicationDtos);

        return RespondPageHolderWrapper.wrap(pair, respondApplicationDtos);
    }

    @Override
    public List<RespondApplicationDto> findUserResponds() {
        return respondedApplicationRepository.findAllRespondedApplicationsByUserEmail(
                authorizedUserService.getAuthentication().getName()
        )
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public RespondPageHolder<VacancyDto, ResumeDto> findEmployerResponds(int page, int size) {
        Authentication authentication = authorizedUserService.getAuthentication();

        Page<RespondedApplication> respondDtoPage = respondedApplicationRepository
                .findAllRespondedApplicationsByEmployerEmail(
                        authentication.getName(),
                        PageRequest.of(page, size)
                );

        Map<VacancyDto, Map<ResumeDto, Boolean>> pair = convertToMapKeyAsVacancyValueAsResume(respondDtoPage);

        return RespondPageHolderWrapper.wrap(pair, respondDtoPage);
    }

    private Map<VacancyDto, Map<ResumeDto, Boolean>> convertToMapKeyAsVacancyValueAsResume(Page<RespondedApplication> respondDtoPage) {
        return respondDtoPage.stream()
                .collect(
                        Collectors.toMap(
                                respondApplicationDto -> vacancyMapper.mapToDto(respondApplicationDto.getVacancy()),
                                respondApplicationDto -> {
                                    List<ResumeDto> resumeDtos = resumeService.findAllResumesByRespondIdAndVacancyId(
                                            respondApplicationDto.getId(),
                                            respondApplicationDto.getVacancy().getId()
                                    );

                                    return resumeDtos.stream()
                                            .collect(Collectors.toMap(
                                                    Function.identity(),
                                                    resumeDto -> respondApplicationDto.getConfirmation()
                                            ));
                                },
                                (k, v) -> {
                                    k.putAll(v);
                                    return k;
                                }
                        )
                );
    }

    @Override
    public List<RespondApplicationDto> findAllResponds() {
        return respondedApplicationRepository.findAll()
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public boolean isRespondExistById(Long respondId) {
        Assert.notNull(respondId, "respondId cannot be null");
        return respondedApplicationRepository.existsById(respondId);
    }

    @Override
    public List<RespondApplicationDto> findAllRespondsByVacancyId(Long vacancyId) {
        Assert.notNull(vacancyId, "vacancy id cannot be null");

        return respondedApplicationRepository.findAllRespondedApplicationsByVacancyId(vacancyId)
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }

    @Override
    public Long findRespondIdByVacancyIdAndResumeId(Long vacancyId, Long resumeId) {
        Assert.isTrue(vacancyId != null && resumeId != null, "vacancyId and resumeId cannot be null");

        return respondedApplicationRepository.findRespondedApplicationByVacancyIdAndResumeId(vacancyId, resumeId)
                .map(RespondedApplication::getId)
                .orElseThrow(() -> new NoSuchElementException("respond not found by"));
    }

    @Override
    public Long findAuthEmployerCreatedRespondsQuantity() {
        return respondedApplicationRepository.findEmployerCreatedRespondsQuantityByEmployerId(authorizedUserService.getAuthorizedUserId())
                .orElse(0L);
    }

    @Override
    public Long findAuthJobSeekerCreatedRespondsQuantity() {
        return respondedApplicationRepository.findJobSeekerCreatedRespondsQuantityByJobSeekerId(authorizedUserService.getAuthorizedUserId())
                .orElse(0L);
    }

    @Override
    public Long findAuthEmployerCreatedConfirmedRespondsQuantity() {
        return respondedApplicationRepository.findEmployerCreatedConfirmedRespondsQuantityByEmployerId(authorizedUserService.getAuthorizedUserId())
                .orElse(0L);
    }

    @Override
    public Long findAuthJobSeekerCreatedConfirmedRespondsQuantity() {
        return respondedApplicationRepository.findJobSeekerCreatedConfirmedRespondsQuantityByJobSeekerId(authorizedUserService.getAuthorizedUserId())
                .orElse(0L);
    }
}
