package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.EducationalInfoDto;
import kg.attractor.jobsearch.model.EducationInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EducationInfoMapper {

    @Mapping(target = "resumeId", source = "resume.id")
    EducationalInfoDto mapToDto(EducationInfo educationInfo);

    @Mapping(target = "resume.id", source = "resumeId")
    EducationInfo mapToEntity(EducationalInfoDto dto);
}
