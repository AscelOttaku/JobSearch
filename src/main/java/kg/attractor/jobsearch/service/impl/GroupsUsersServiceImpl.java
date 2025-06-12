package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.GroupsUsersDto;
import kg.attractor.jobsearch.dto.mapper.impl.GroupsUserMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.model.Groups;
import kg.attractor.jobsearch.model.GroupsUsers;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.GroupsUsersRepository;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.service.GroupsUsersService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class GroupsUsersServiceImpl implements GroupsUsersService {
    private final GroupsUsersRepository groupsUsersRepository;
    private final GroupsUserMapper groupsUserMapper;
    private GroupsService groupsService;
    private final UserService userService;
    private final UserMapper userMapper;

    @Autowired
    public void setGroupsService(@Lazy GroupsService groupsService) {
        this.groupsService = groupsService;
    }

    @Override
    public GroupsUsersDto joinGroup(Long groupId, Long userId) {
        groupsService.isGroupExistById(groupId);
        userService.isUserExistById(userId);

        if (groupsUsersRepository.existsByGroupIdAndUserId(groupId, userId))
            throw new IllegalArgumentException("User already joined the group");

        GroupsUsers groupsUsers = new GroupsUsers();
        Groups groups = new Groups();
        groups.setId(groupId);
        groupsUsers.setGroup(groups);

        User user = userMapper.mapToEntity(userService.findUserById(userId));
        groupsUsers.setUser(user);

        GroupsUsers save = groupsUsersRepository.save(groupsUsers);
        return groupsUserMapper.mapToDto(save);
    }

    @Override
    public boolean isUserJoinedGroup(Long groupId, Long userId) {
        return groupsUsersRepository.existsByGroupIdAndUserId(groupId, userId);
    }

    @Override
    public Long findMembersCountByGroupId(Long groupId) {
        Assert.isTrue(groupId != null && groupId > 0, "groupId must not be null and greater than 0");
        return groupsUsersRepository.findMembersCountByGroupId(groupId);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class)
    @Override
    public void leaveGroup(Long groupId, Long userId) {
        groupsService.isGroupExistById(groupId);
        userService.isUserExistById(userId);

        groupsUsersRepository.deleteByGroupIdAndUserId(groupId, userId);
    }
}
