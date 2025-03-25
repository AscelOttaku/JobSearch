package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CreateWorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.stereotype.Service;

@Service
public class CreateWorkExperienceInfoMapper implements Mapper<CreateWorkExperienceInfoDto, WorkExperienceInfo> {
    @Override
    public CreateWorkExperienceInfoDto mapToDto(WorkExperienceInfo entity) {
        return CreateWorkExperienceInfoDto.builder()
                .years(entity.getYears())
                .companyName(entity.getCompanyName())
                .position(entity.getPosition())
                .responsibilities(entity.getResponsibilities())
                .build();
    }

    @Override
    public WorkExperienceInfo mapToEntity(CreateWorkExperienceInfoDto dto) {
        WorkExperienceInfo entity = new WorkExperienceInfo();
        entity.setYears(dto.getYears());
        entity.setCompanyName(dto.getCompanyName());
        entity.setPosition(dto.getPosition());
        entity.setResponsibilities(dto.getResponsibilities());
        return entity;
    }
}
