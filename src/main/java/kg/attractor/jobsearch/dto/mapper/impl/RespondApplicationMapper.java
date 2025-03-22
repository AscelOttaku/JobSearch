package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.RespondedApplication;
import org.springframework.stereotype.Service;

@Service
public class RespondApplicationMapper implements Mapper<RespondApplicationDto, RespondedApplication> {
    @Override
    public RespondApplicationDto mapToDto(RespondedApplication entity) {
        return RespondApplicationDto.builder()
                .resumeId(entity.getResumeId())
                .vacancyId(entity.getVacancyId())
                .confirmation(entity.getConfirmation())
                .build();
    }

    @Override
    public RespondedApplication mapToEntity(RespondApplicationDto dto) {
        RespondedApplication entity = new RespondedApplication();
        entity.setResumeId(dto.getResumeId());
        entity.setVacancyId(dto.getVacancyId());
        entity.setConfirmation(dto.isConfirmation());
        return entity;
    }
}
