package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.VideoDto;
import kg.attractor.jobsearch.model.Video;
import org.mapstruct.*;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VideoMapper {

    @Mapping(target = "channelDto.id", source = "channel.id")
    VideoDto mapToDto(Video video);

    @Mapping(target = "channel.id", source = "channelDto.id")
    Video mapToEntity(VideoDto videoDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "channel", source = "channelDto")
    void updateEntity(VideoDto videoDto, @MappingTarget Video video);
}
