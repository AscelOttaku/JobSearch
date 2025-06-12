package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GroupsUsersDto {
    private Long id;
    private GroupsDto group;
    private UserDto user;
}
