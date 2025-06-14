package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.GroupsUsersDto;

import java.util.List;

public interface GroupsUsersService {
    GroupsUsersDto joinGroup(Long groupId, Long userId);

    GroupsUsersDto joinGroupByLink(Long groupId, String token);

    boolean isUserJoinedGroup(Long groupId, Long userId);

    Long findMembersCountByGroupId(Long groupId);

    void leaveGroup(Long groupId, Long userId);

    List<GroupsUsersDto> findAllMembersByGroupId(Long groupId);
}
