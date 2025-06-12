package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.GroupsMessagesDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.dto.mapper.utils.MapperUtil;
import kg.attractor.jobsearch.model.GroupsMessages;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class, MapperUtil.class})
public interface GroupsMessagesMapper {

    @Mapping(target = "groupId", source = "group.id")
    @Mapping(target = "createdAt", qualifiedByName = "convertLocalDateTimeToString", source = "createdTime")
    @Mapping(target = "updatedAt", qualifiedByName = "convertLocalDateTimeToString", source = "updatedTime")
    GroupsMessagesDto mapToDto(GroupsMessages groupsMessages);

    @Mapping(target = "group.id", source = "groupId")
    @Mapping(target = "createdTime", qualifiedByName = "convertStringToLocalDateTime", source = "createdAt")
    @Mapping(target = "updatedTime", qualifiedByName = "convertStringToLocalDateTime", source = "updatedAt")
    GroupsMessages mapToEntity(GroupsMessagesDto groupsMessagesDto);
}
