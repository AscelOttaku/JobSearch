package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.service.ResumeService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondApplicationMapper implements Mapper<RespondApplicationDto, RespondedApplication> {
    private final ResumeService resumeService;
    private final ResumeMapper resumeMapper;
    private final VacancyMapper vacancyMapper;
    private final VacancyService vacancyService;

    @Override
    public RespondApplicationDto mapToDto(RespondedApplication respondApplication) {
        return RespondApplicationDto.builder()
                .resumeId(respondApplication.getResume().getId())
                .vacancyId(respondApplication.getVacancy().getId())
                .confirmation(respondApplication.getConfirmation())
                .build();
    }

    @Override
    public RespondedApplication mapToEntity(RespondApplicationDto dto) {
        RespondedApplication entity = new RespondedApplication();
        entity.setResume(resumeMapper.mapToEntity(resumeService.findResumeById(dto.getResumeId())));
        entity.setVacancy(vacancyMapper.mapToEntity(vacancyService.findVacancyById(dto.getVacancyId())));
        entity.setConfirmation(dto.getConfirmation());
        return entity;
    }
}
