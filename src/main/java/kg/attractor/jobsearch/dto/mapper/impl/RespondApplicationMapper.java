package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.ResumeDto;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.RespondedApplication;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.Vacancy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RespondApplicationMapper implements Mapper<RespondApplicationDto, RespondedApplication> {
    private final Mapper<ResumeDto, Resume> resumeMapper;
    private final Mapper<VacancyDto, Vacancy> vacancyMapper;

    @Override
    public RespondApplicationDto mapToDto(RespondedApplication entity) {
        return RespondApplicationDto.builder()
                .resumeDto(resumeMapper.mapToDto(entity.getResume()))
                .vacancyDto(vacancyMapper.mapToDto(entity.getVacancy()))
                .confirmation(entity.getConfirmation())
                .build();
    }

    @Override
    public RespondedApplication mapToEntity(RespondApplicationDto dto) {
        RespondedApplication entity = new RespondedApplication();
        entity.setResume(resumeMapper.mapToEntity(dto.getResumeDto()));
        entity.setVacancy(vacancyMapper.mapToEntity(dto.getVacancyDto()));
        entity.setConfirmation(dto.isConfirmation());
        return entity;
    }
}
