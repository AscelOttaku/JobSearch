package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.GroupsDto;
import kg.attractor.jobsearch.model.Groups;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GroupsMapper {

    @Mapping(target = "admin.userId", source = "admin.userId")
    GroupsDto mapToDto(Groups groups);

    @Mapping(target = "admin.userId", source = "admin.userId")
    Groups mapToEntity(GroupsDto groupsDto);
}
