package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.UpdatedEducationalInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.stereotype.Service;

@Service
public class UpdateEducationInfoMapper implements Mapper<UpdatedEducationalInfoDto, EducationInfo> {
    @Override
    public UpdatedEducationalInfoDto mapToDto(EducationInfo entity) {
        return UpdatedEducationalInfoDto.builder()
                .id(entity.getId())
                .institution(entity.getInstitution())
                .program(entity.getProgram())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .degree(entity.getDegree())
                .build();
    }

    @Override
    public EducationInfo mapToEntity(UpdatedEducationalInfoDto dto) {
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setId(dto.getId());
        educationInfo.setInstitution(dto.getInstitution());
        educationInfo.setProgram(dto.getProgram());
        educationInfo.setStartDate(dto.getStartDate());
        educationInfo.setEndDate(dto.getEndDate());
        educationInfo.setDegree(dto.getDegree());
        return educationInfo;
    }
}
