package kg.attractor.jobsearch.service.impl;

import jakarta.transaction.Transactional;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.RespondApplicationMapper;
import kg.attractor.jobsearch.enums.Roles;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import kg.attractor.jobsearch.repository.RespondedApplicationRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.RespondService;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RespondServiceImpl implements RespondService {
    private final RespondedApplicationRepository respondedApplicationRepository;
    private final RespondApplicationMapper respondApplicationMapper;
    private final ResumeService resumeService;
    private final AuthorizedUserService authorizedUserService;
    private final VacancyService vacancyService;

    @Transactional
    @Override
    public void createRespond(RespondApplicationDto respondApplicationDto) {
        UserDto userDto = authorizedUserService.getAuthorizedUser();

        if (!userDto.getAccountType().equals(Roles.JOB_SEEKER.getValue()))
            throw new IllegalArgumentException("User who's account type is not a job seeker cannot create a respond");

        ResumeDto resumeDto = resumeService.findResumeById(respondApplicationDto.getResumeId());
        VacancyDto vacancyDto = vacancyService.findVacancyById(respondApplicationDto.getVacancyId());

        if (!resumeDto.getUserId().equals(userDto.getUserId()))
            throw new IllegalArgumentException("Resume doesn't belongs to authorized user");
        
        respondedApplicationRepository.save(mapToRespondedApplication(resumeDto, vacancyDto));
    }

    private RespondedApplication mapToRespondedApplication(ResumeDto resumeDto, VacancyDto vacancyDto) {
        Resume resume = new Resume();
        resume.setId(resumeDto.getId());

        Vacancy vacancy = new Vacancy();
        vacancy.setId(vacancyDto.getVacancyId());

        RespondedApplication respondedApplication = new RespondedApplication();
        respondedApplication.setResume(resume);
        respondedApplication.setVacancy(vacancy);
        respondedApplication.setConfirmation(false);
        return respondedApplication;
    }

    @Override
    public List<RespondApplicationDto> findAllActiveResponsesByUserId(Long userId) {
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
}
