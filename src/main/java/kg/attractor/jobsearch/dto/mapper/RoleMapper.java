package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.RoleDto;
import kg.attractor.jobsearch.model.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleDto mapToDto(Role role);

    Role mapToEntity(RoleDto roleDto);
}
