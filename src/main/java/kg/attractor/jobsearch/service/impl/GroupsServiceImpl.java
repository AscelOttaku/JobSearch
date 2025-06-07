package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.GroupsDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.GroupsMapper;
import kg.attractor.jobsearch.model.Groups;
import kg.attractor.jobsearch.repository.GroupsRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class GroupsServiceImpl implements GroupsService {
    private final GroupsRepository groupsRepository;
    private final GroupsMapper groupsMapper;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public List<GroupsDto> findAllGroups() {
        return groupsRepository.findAll().stream()
                .map(groupsMapper::mapToDto)
                .toList();
    }

    @Override
    public GroupsDto createGroups(GroupsDto groupsDto) throws IOException {
        groupsDto.setAdmin(UserDto.builder()
                .userId(authorizedUserService.getAuthorizedUserId())
                .build());

        Groups groups = groupsMapper.mapToEntity(groupsDto);
        groups.setLogo(FileUtil.uploadFile(groupsDto.getImage()));

        return groupsMapper.mapToDto(groupsRepository.save(groups));
    }

    @Override
    public GroupsDto updateGroups(GroupsDto groupsDto) throws IOException {
        Groups groups = groupsRepository.findById(groupsDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Groups not found by id: " + groupsDto.getId()));

        Long authUserId = authorizedUserService.getAuthorizedUserId();

        if (!groups.getAdmin().getUserId().equals(authUserId))
            throw new IllegalArgumentException("group doesn't belongs to user");

        Groups updatedGroups = groupsMapper.mapToEntity(groupsDto);

        if (groupsDto.getImage() != null) {
            FileUtil.deleteFile("data/photos/" + groups.getLogo());
            updatedGroups.setLogo(FileUtil.uploadFile(groupsDto.getImage()));
        }

        return groupsMapper.mapToDto(groupsRepository.save(updatedGroups));
    }

    @Override
    public GroupsDto findGroupsById(Long id) {
        Assert.notNull(id, "id must not be null");

        return groupsRepository.findById(id)
                .map(groupsMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Groups not found by id: " + id));
    }

    @Override
    public void deleteGroupsById(Long id) {
        groupsRepository.deleteById(id);
    }
}
