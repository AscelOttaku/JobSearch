package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondApplicationMapper implements Mapper<RespondApplicationDto, RespondedApplication> {

    @Override
    public RespondApplicationDto mapToDto(RespondedApplication respondApplication) {
        return RespondApplicationDto.builder()
                .id(respondApplication.getId())
                .resumeId(respondApplication.getResume().getId())
                .vacancyId(respondApplication.getVacancy().getId())
                .confirmation(respondApplication.getConfirmation())
                .build();
    }

    @Override
    public RespondedApplication mapToEntity(RespondApplicationDto dto) {
        RespondedApplication entity = new RespondedApplication();

        Vacancy vacancy = new Vacancy();
        vacancy.setId(dto.getVacancyId());

        Resume resume = new Resume();
        resume.setId(dto.getResumeId());

        entity.setVacancy(vacancy);
        entity.setResume(resume);
        entity.setConfirmation(dto.getConfirmation());
        return entity;
    }
}
