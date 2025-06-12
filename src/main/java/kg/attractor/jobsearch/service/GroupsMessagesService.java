package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.GroupsMessagesDto;

import java.util.List;

public interface GroupsMessagesService {
    List<GroupsMessagesDto> findALlGroupsMessageByGroupId(Long groupId);

    void createMessage(GroupsMessagesDto groupsMessagesDto);
}
