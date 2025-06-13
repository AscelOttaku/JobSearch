package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.GroupsMessagesDto;
import kg.attractor.jobsearch.dto.mapper.GroupsMessagesMapper;
import kg.attractor.jobsearch.model.GroupsMessages;
import kg.attractor.jobsearch.repository.GroupsMessagesRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.GroupsMessagesService;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupsMessagesServiceImpl implements GroupsMessagesService {
    private final GroupsMessagesRepository groupsMessagesRepository;
    private final GroupsMessagesMapper groupsMessagesMapper;
    private final GroupsService groupsService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public List<GroupsMessagesDto> findALlGroupsMessageByGroupId(Long groupId) {
        return groupsMessagesRepository.findAllByGroupId(groupId).stream()
                .map(groupsMessagesMapper::mapToDto)
                .toList();
    }

    @Override
    public void createMessage(GroupsMessagesDto groupsMessagesDto) {
        groupsService.isGroupExistById(groupsMessagesDto.getGroupId());
        groupsMessagesDto.setOwner(authorizedUserService.getAuthorizedUser());
        groupsMessagesRepository.save(groupsMessagesMapper.mapToEntity(groupsMessagesDto));
    }

    @Override
    public void createMessageFile(Long groupId, MultipartFile multipartFile) {
        Assert.isTrue(groupId != null && groupId > 0, "Group ID must be a positive number");
        groupsService.isGroupExistById(groupId);

        var fileInfoDto = FileUtil.createFileForMessages(multipartFile);

        GroupsMessagesDto groupsMessagesDto = GroupsMessagesDto.builder()
                .groupId(groupId)
                .owner(authorizedUserService.getAuthorizedUser())
                .message(fileInfoDto.getFilePath())
                .messageType(fileInfoDto.getMessageType())
                .build();

        GroupsMessages groupsMessages = groupsMessagesMapper.mapToEntity(groupsMessagesDto);
        groupsMessagesRepository.save(groupsMessages);
    }
}
