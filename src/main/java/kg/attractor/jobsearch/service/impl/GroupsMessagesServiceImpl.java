package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.GroupsDto;
import kg.attractor.jobsearch.dto.GroupsMessagesDto;
import kg.attractor.jobsearch.dto.mapper.GroupsMessagesMapper;
import kg.attractor.jobsearch.enums.MessageType;
import kg.attractor.jobsearch.model.GroupsMessages;
import kg.attractor.jobsearch.repository.GroupsMessagesRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.GroupsMessagesService;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.util.FileUtil;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

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

        if (Util.isMessageLink(groupsMessagesDto.getMessage())) groupsMessagesDto.setMessageType(MessageType.LINK);
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

    @Override
    public void deleteMessageById(Long messageId) {
        Assert.isTrue(messageId != null && messageId > 0, "Message ID must be a positive number");
        GroupsMessages groupsMessages = groupsMessagesRepository.findById(messageId)
                .orElseThrow(() -> new NoSuchElementException("Message not found by id: " + messageId));

        if (!groupsMessages.getOwner().getUserId().equals(authorizedUserService.getAuthorizedUserId()))
            throw new IllegalArgumentException("You can delete only your own messages");

        groupsMessagesRepository.delete(groupsMessages);
    }

    @Override
    public void deleteAllMessagesByGroupId(Long groupId) {
        groupsService.isGroupExistById(groupId);

        GroupsDto groupsDto = groupsService.findGroupsById(groupId);
        if (!groupsDto.getAdmin().getUserId().equals(authorizedUserService.getAuthorizedUserId()))
            throw new IllegalArgumentException("You can delete messages only in groups you administer");

        groupsMessagesRepository.deleteAllMessagesByGroupId(groupId);
    }

    @Transactional
    @Override
    public void updateMessage(GroupsMessagesDto groupsMessagesDto) {
        if (groupsMessagesDto.getId() == null)
            throw new IllegalArgumentException("Message ID or owner must not be null");

        GroupsMessages groupsMessages = groupsMessagesRepository.findById(
                        groupsMessagesDto.getId()
                )
                .orElseThrow(() -> new NoSuchElementException("Message not found by id: " + groupsMessagesDto.getId()));

        groupsMessages.setMessage(groupsMessagesDto.getMessage());
        if (Util.isMessageLink(groupsMessagesDto.getMessage())) groupsMessages.setMessageType(MessageType.LINK);
        else groupsMessages.setMessageType(MessageType.MESSAGES);
    }

    @Override
    public GroupsMessagesDto findGroupsMessageById(Long messageId) {
        return groupsMessagesRepository.findById(messageId)
                .map(groupsMessagesMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Message not found by id: " + messageId));
    }
}
