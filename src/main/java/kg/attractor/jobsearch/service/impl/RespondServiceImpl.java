package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.RespondedApplicationRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RespondServiceImpl implements RespondService {
    private final RespondedApplicationRepository respondedApplicationRepository;
    private final RespondApplicationMapper respondApplicationMapper;
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public RespondApplicationDto createRespond(RespondApplicationDto respondApplicationDto) {

        UserDto userDto = authorizedUserService.getAuthorizedUser();

        if (!userDto.getAccountType().equalsIgnoreCase("JobSeeker"))
            throw new CustomIllegalArgException(
                    "User who's account type is not a job seeker cannot create a respond",
                    CustomBindingResult.builder()
                            .className(User.class.getSimpleName())
                            .fieldName("accountType")
                            .rejectedValue(userDto.getAccountType())
                            .build()
            );

        if (checkIsRespondApplicationExist(respondApplicationDto)) {

            boolean isResumeByIdExist = resumeService.isResumeExist(respondApplicationDto.getResumeDto().getId());
            boolean isVacancyByIdExist = vacancyService.isVacancyExist(respondApplicationDto.getVacancyDto().getVacancyId());

            if (isResumeByIdExist && isVacancyByIdExist) {

                boolean isResumeBelongsToAuthorizedUser = resumeService.findUserCreatedResumes()
                        .stream()
                        .anyMatch(resumeDto -> Objects.equals(resumeDto.getId(), respondApplicationDto.getResumeDto().getId()));

                if (!isResumeBelongsToAuthorizedUser)
                    throw new CustomIllegalArgException(
                            "Resume doesn't belongs to authorized user",
                            CustomBindingResult.builder()
                                    .className(Resume.class.getSimpleName())
                                    .fieldName("resumeId")
                                    .rejectedValue(respondApplicationDto.getResumeDto().getId())
                                    .build()
                    );

                RespondedApplication respondedApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);
                Long respondId = respondedApplicationRepository.save(respondedApplication).getId();

                return respondedApplicationRepository.findById(respondId)
                        .map(respondApplicationMapper::mapToDto)
                        .orElseThrow(() -> new NoSuchElementException("Respond doesn't found by id " + respondId));
            }
        }

        throw new IllegalArgumentException("Invalid respondApplicationDto");
    }

    public boolean checkIsRespondApplicationExist(RespondApplicationDto respondApplicationDto) {
        RespondedApplication respondApplication = respondApplicationMapper.mapToEntity(respondApplicationDto);
        var allData = respondedApplicationRepository.findAll();

        return allData.stream()
                .noneMatch(data ->
                        data.getId().equals(respondApplication.getId()));
    }

    @Override
    public List<RespondApplicationDto> findAllActiveResponsesByUserId(Long userId) {
        return respondedApplicationRepository.findActiveRespondedApplicationsByUserId(userId)
                .stream()
                .map(respondApplicationMapper::mapToDto)
                .toList();
    }
}
