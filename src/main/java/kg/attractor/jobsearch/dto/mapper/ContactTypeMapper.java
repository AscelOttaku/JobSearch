package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.ContactTypeDto;
import kg.attractor.jobsearch.model.ContactType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactTypeMapper {

    @Mapping(target = "contactTypeId", source = "id")
    ContactTypeDto mapToDto(ContactType contactType);

    @Mapping(target = "id", source = "contactTypeId")
    ContactType mapToEntity(ContactTypeDto contactTypeDto);
}
