package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.GroupsDto;

import java.io.IOException;
import java.util.List;

public interface GroupsService {
    List<GroupsDto> findAllGroups();

    GroupsDto createGroups(GroupsDto groupsDto) throws IOException;

    GroupsDto updateGroups(GroupsDto groupsDto) throws IOException;

    GroupsDto findGroupsById(Long id);

    void deleteGroupsById(Long id);

    void isGroupExistById(Long id);

    GroupsDto findGroupsByMessageId(Long messageId);
}
