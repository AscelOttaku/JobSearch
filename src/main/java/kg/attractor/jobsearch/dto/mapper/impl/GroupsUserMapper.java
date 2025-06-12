package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.GroupsUsersDto;
import kg.attractor.jobsearch.dto.mapper.GroupsMapper;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.GroupsUsers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GroupsUserMapper implements Mapper<GroupsUsersDto, GroupsUsers> {
    private final GroupsMapper groupsMapper;
    private final UserMapper userMapper;

    @Override
    public GroupsUsersDto mapToDto(GroupsUsers entity) {
        return GroupsUsersDto.builder()
                .id(entity.getId())
                .group(groupsMapper.mapToDto(entity.getGroup()))
                .user(userMapper.mapToDto(entity.getUser()))
                .build();
    }

    @Override
    public GroupsUsers mapToEntity(GroupsUsersDto dto) {
        GroupsUsers entity = new GroupsUsers();
        entity.setId(dto.getId());
        entity.setGroup(groupsMapper.mapToEntity(dto.getGroup()));
        entity.setUser(userMapper.mapToEntity(dto.getUser()));
        return entity;
    }
}
