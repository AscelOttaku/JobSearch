package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.WorkExperienceInfoDto;
import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface WorkExperienceMapper {

    @Mapping(target = "resumeId", source = "resume.id")
    WorkExperienceInfoDto mapToDto(WorkExperienceInfo workExperienceInfo);

    @Mapping(target = "resume.id", source = "resumeId")
    WorkExperienceInfo mapToEntity(WorkExperienceInfoDto workExperienceInfoDto);
}
