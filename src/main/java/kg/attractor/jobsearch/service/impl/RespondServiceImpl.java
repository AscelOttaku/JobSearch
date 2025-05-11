package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.*;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.dto.mapper.impl.VacancyMapper;
import kg.attractor.jobsearch.enums.Roles;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.repository.RespondedApplicationRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import kg.attractor.jobsearch.validators.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RespondServiceImpl implements RespondService {
    private final RespondedApplicationRepository respondedApplicationRepository;
    private final RespondApplicationMapper respondApplicationMapper;
    private final ResumeService resumeService;
    private final AuthorizedUserService authorizedUserService;
    private final VacancyService vacancyService;
    private final PageHolderWrapper pageHolderWrapper;
    private final VacancyMapper vacancyMapper;

    @Transactional
    @Override
    public void createRespond(RespondApplicationDto respondApplicationDto) {
        UserDto userDto = authorizedUserService.getAuthorizedUser();

        if (!userDto.getAccountType().equals(Roles.JOB_SEEKER.getValue()))
            throw new IllegalArgumentException("User who's account type is not a job seeker cannot create a respond");

        ResumeDto resumeDto = resumeService.findResumeById(respondApplicationDto.getResumeId());
        vacancyService.findVacancyById(respondApplicationDto.getVacancyId());

        if (!resumeDto.getUserId().equals(userDto.getUserId()))
            throw new IllegalArgumentException("Resume doesn't belongs to authorized user");

        respondApplicationDto.setConfirmation(Boolean.FALSE);
        RespondedApplication respondedApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);

        respondedApplicationRepository.save(respondedApplication);
    }

    @Override
    public List<RespondApplicationDto> findAllActiveResponsesByUserId(Long userId) {
        Validator.isValidId(userId);

        return respondedApplicationRepository.findActiveRespondedApplicationsByUserId(userId)
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
    public PageHolder<RespondApplicationDto> findUserResponds(int page, int size) {
        Authentication authentication = authorizedUserService.getAuthentication();

        Page<RespondApplicationDto> respondApplicationDtos = respondedApplicationRepository
                .findAllRespondedApplicationsByUserEmail(
                        authentication.getName(),
                        PageRequest.of(page, size))
                .map(respondApplicationMapper::mapToDto);

        return pageHolderWrapper.wrapPageHolder(respondApplicationDtos, null);
    }

    @Override
    public EmployerRespondsPageHolder<VacancyDto, ResumeDto> findEmployerResponds(int page, int size) {
        Authentication authentication = authorizedUserService.getAuthentication();

        Page<RespondedApplication> respondDtoPage = respondedApplicationRepository
                .findAllRespondedApplicationsByEmployerEmail(
                        authentication.getName(),
                        PageRequest.of(page, size)
                );

        Map<VacancyDto, List<ResumeDto>> responds = respondDtoPage.stream()
                .collect(
                        Collectors.toMap(
                                respondApplicationDto -> vacancyMapper.mapToDto(respondApplicationDto.getVacancy()),
                                respondApplicationDto -> resumeService.findAllResumesByRespondIdAndVacancyId(
                                        respondApplicationDto.getId(),
                                        respondApplicationDto.getVacancy().getId()
                                ),
                                (k, v) -> {
                                    k.addAll(v);
                                    return k;
                                }
                        )
                );

        return EmployerRespondsPageHolder.<VacancyDto, ResumeDto>builder()
                .content(responds)
                .page(respondDtoPage.getNumber())
                .size(respondDtoPage.getSize())
                .totalPages(respondDtoPage.getTotalPages())
                .hasPreviousPage(respondDtoPage.hasPrevious())
                .hasNextPage(respondDtoPage.hasNext())
                .filterType(null)
                .build();
    }
}
