package kg.attractor.jobsearch.dto.mapper;

import kg.attractor.jobsearch.dto.LikeDto;
import kg.attractor.jobsearch.model.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    @Mapping(target = "user.userId", source = "userId")
    @Mapping(target = "video.id", source = "videoId")
    Like mapToEntity(LikeDto dto);

    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "videoId", source = "video.id")
    LikeDto mapToDto(Like entity);
}
