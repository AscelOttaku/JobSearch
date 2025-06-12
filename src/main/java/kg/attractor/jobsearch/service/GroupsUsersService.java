package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.GroupsUsersDto;

public interface GroupsUsersService {
    GroupsUsersDto joinGroup(Long groupId, Long userId);

    boolean isUserJoinedGroup(Long groupId, Long userId);

    Long findMembersCountByGroupId(Long groupId);

    void leaveGroup(Long groupId, Long userId);
}
