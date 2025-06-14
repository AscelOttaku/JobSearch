package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.GroupsDto;
import kg.attractor.jobsearch.dto.GroupsUsersDto;
import kg.attractor.jobsearch.dto.mapper.impl.GroupsUserMapper;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.model.Groups;
import kg.attractor.jobsearch.model.GroupsUsers;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.GroupsUsersRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.service.GroupsUsersService;
import kg.attractor.jobsearch.service.UserService;
import kg.attractor.jobsearch.storage.TemporalStorage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsUsersServiceImpl implements GroupsUsersService {
    private final GroupsUsersRepository groupsUsersRepository;
    private final GroupsUserMapper groupsUserMapper;
    private GroupsService groupsService;
    private final UserService userService;
    private final UserMapper userMapper;
    private TemporalStorage temporalStorage;

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
    public GroupsUsersDto joinGroupByLink(Long groupId, String token) {
        GroupsDto groupsById = groupsService.findGroupsById(groupId);
        if (groupsById.getAdmin().getUserId().equals(userService.getAuthUserId()))
            throw new IllegalArgumentException("You are the admin of this group, you cannot join it using a link");

        String storedToken = temporalStorage.getOptionalTemporalData("groupToken_" + groupId, String.class)
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));

        if (!storedToken.endsWith("/token/".concat(token)))
            throw new IllegalArgumentException("Invalid or expired token");

        LocalDateTime createdTime = temporalStorage.getTemporalData("groupToken_" + groupId + "_created", LocalDateTime.class);

        if (createdTime.isBefore(LocalDateTime.now().minusHours(1))) {
            temporalStorage.removeTemporalData("groupToken_" + groupId);
            temporalStorage.removeTemporalData("groupToken_" + groupId + "_created");
            throw new IllegalArgumentException("Token expired");
        }
        return joinGroup(groupId, userService.getAuthUserId());
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

    @Override
    public List<GroupsUsersDto> findAllMembersByGroupId(Long groupId) {
        return groupsUsersRepository.findAllGroupsUsersByGroupId(groupId)
                .stream()
                .map(groupsUserMapper::mapToDto)
                .toList();
    }

    @Transactional
    @Override
    public void deleteGroupMemberById(Long groupId, Long userId) {
        var adminId = groupsService.findGroupsById(groupId)
                .getAdmin()
                .getUserId();

        if (!userService.getAuthUserId().equals(adminId))
            throw new IllegalArgumentException("Only group admin can delete members");

        if (!groupsUsersRepository.existsByGroupIdAndUserId(groupId, userId))
            throw new IllegalArgumentException("User is not a member of the group");

        groupsUsersRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    @Autowired
    public void setTemporalStorage(TemporalStorage temporalStorage) {
        this.temporalStorage = temporalStorage;
    }
}
