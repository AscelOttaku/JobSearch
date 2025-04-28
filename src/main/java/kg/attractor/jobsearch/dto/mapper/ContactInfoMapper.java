package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.ContactInfoDto;
import kg.attractor.jobsearch.model.ContactInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ContactTypeMapper.class})
public interface ContactInfoMapper {

    @Mapping(target = "resumeId", source = "resume.id")
    ContactInfoDto mapToDto(ContactInfo entity);

    @Mapping(target = "resume.id", source = "resumeId")
    ContactInfo mapToEntity(ContactInfoDto dto);
}
