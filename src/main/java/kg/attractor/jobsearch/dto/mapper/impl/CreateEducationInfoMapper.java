package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CreateEducationInfoDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.EducationInfo;
import org.springframework.stereotype.Service;

@Service
public class CreateEducationInfoMapper implements Mapper<CreateEducationInfoDto, EducationInfo> {
    @Override
    public CreateEducationInfoDto mapToDto(EducationInfo entity) {
        return CreateEducationInfoDto.builder()
                .institution(entity.getInstitution())
                .program(entity.getProgram())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .degree(entity.getDegree())
                .build();
    }

    @Override
    public EducationInfo mapToEntity(CreateEducationInfoDto dto) {
        EducationInfo educationInfo = new EducationInfo();
        educationInfo.setInstitution(dto.getInstitution());
        educationInfo.setProgram(dto.getProgram());
        educationInfo.setStartDate(dto.getStartDate());
        educationInfo.setEndDate(dto.getEndDate());
        educationInfo.setDegree(dto.getDegree());
        return educationInfo;
    }
}
