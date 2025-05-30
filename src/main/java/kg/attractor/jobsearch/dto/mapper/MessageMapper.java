package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.model.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface MessageMapper {

    @Mapping(target = "respondedApplicationId", source = "respondedApplication.id")
    @Mapping(target = "userDto", source = "owner")
    MessageDto mapToDto(Message message);

    @Mapping(target = "respondedApplication.id", source = "respondedApplicationId")
    @Mapping(target = "owner", source = "userDto")
    Message mapToEntity(MessageDto messageDto);
}
