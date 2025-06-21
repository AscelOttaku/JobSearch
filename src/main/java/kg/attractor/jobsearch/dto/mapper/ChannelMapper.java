package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.ChannelDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.model.Channel;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface ChannelMapper {

    @Mapping(target = "userDto", source = "user")
    ChannelDto mapToDto(Channel channel);

    @Mapping(target = "user", source = "userDto")
    Channel mapToEntity(ChannelDto channelDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateChannelDto(ChannelDto source, @MappingTarget Channel target);
}
