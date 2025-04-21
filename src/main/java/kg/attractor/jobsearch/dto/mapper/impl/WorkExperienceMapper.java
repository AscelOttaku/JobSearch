package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Resume;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.stereotype.Service;

@Service
public class WorkExperienceMapper implements Mapper<WorkExperienceInfoDto, WorkExperienceInfo> {
    @Override
    public WorkExperienceInfoDto mapToDto(WorkExperienceInfo entity) {
        return WorkExperienceInfoDto.builder()
                .id(entity.getId())
                .resumeId(entity.getResume().getId())
                .years(entity.getYears())
                .companyName(entity.getCompanyName())
                .position(entity.getPosition())
                .responsibilities(entity.getResponsibilities())
                .build();
    }

    @Override
    public WorkExperienceInfo mapToEntity(WorkExperienceInfoDto dto) {
        Resume resume = new Resume();
        resume.setId(dto.getResumeId());

        WorkExperienceInfo entity = new WorkExperienceInfo();
        entity.setId(dto.getId());
        entity.setResume(resume);
        entity.setYears(dto.getYears());
        entity.setCompanyName(dto.getCompanyName());
        entity.setPosition(dto.getPosition());
        entity.setResponsibilities(dto.getResponsibilities());
        return entity;
    }
}
