package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.EducationInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.stereotype.Service;

@Service
public class EducationInfoMapper implements Mapper<EducationInfoDto, EducationInfo> {
    @Override
    public EducationInfoDto mapToDto(EducationInfo entity) {
        return EducationInfoDto.builder()
                .institution(entity.getInstitution())
                .program(entity.getProgram())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .degree(entity.getDegree())
                .build();
    }

    @Override
    public EducationInfo mapToEntity(EducationInfoDto dto) {
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setInstitution(dto.getInstitution());
        educationInfo.setProgram(dto.getProgram());
        educationInfo.setStartDate(dto.getStartDate());
        educationInfo.setEndDate(dto.getEndDate());
        educationInfo.setDegree(dto.getDegree());
        return educationInfo;
    }
}
