package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.AuthorityDto;
import kg.attractor.jobsearch.model.Authority;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorityMapper {
    AuthorityDto mapToDto(Authority authority);
    Authority mapToEntity(AuthorityDto authorityDto);
}
