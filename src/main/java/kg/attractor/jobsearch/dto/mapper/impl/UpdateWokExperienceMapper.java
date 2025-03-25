package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.UpdateWorkExperienceInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.stereotype.Service;

@Service
public class UpdateWokExperienceMapper implements Mapper<UpdateWorkExperienceInfoDto, WorkExperienceInfo> {
    @Override
    public UpdateWorkExperienceInfoDto mapToDto(WorkExperienceInfo entity) {
        return UpdateWorkExperienceInfoDto.builder()
                .id(entity.getId())
                .years(entity.getYears())
                .companyName(entity.getCompanyName())
                .position(entity.getPosition())
                .responsibilities(entity.getResponsibilities())
                .build();
    }

    @Override
    public WorkExperienceInfo mapToEntity(UpdateWorkExperienceInfoDto dto) {
        WorkExperienceInfo entity = new WorkExperienceInfo();
        entity.setId(dto.getId());
        entity.setYears(dto.getYears());
        entity.setCompanyName(dto.getCompanyName());
        entity.setPosition(dto.getPosition());
        entity.setResponsibilities(dto.getResponsibilities());
        return entity;
    }
}
