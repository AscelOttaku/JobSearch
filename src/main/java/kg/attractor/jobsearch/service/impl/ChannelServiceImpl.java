package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.ChannelDto;
import kg.attractor.jobsearch.dto.mapper.ChannelMapper;
import kg.attractor.jobsearch.model.Channel;
import kg.attractor.jobsearch.repository.ChannelRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.ChannelService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {
    private final ChannelRepository channelRepository;
    private final AuthorizedUserService authorizedUserService;
    private final ChannelMapper channelMapper;

    @Override
    public ChannelDto createChannel(ChannelDto channelDto) throws IOException {
        if (channelRepository.findChannelByUserId(authorizedUserService.getAuthorizedUserId())
                .isPresent()) {
            throw new IllegalStateException("Channel already exists for user " + authorizedUserService.getAuthorizedUserId());
        }
        channelDto.setUserDto(authorizedUserService.getAuthorizedUser());

        var avatar = channelDto.getAvatar();
        if (avatar != null && !avatar.isEmpty()) {
            String avatarPath = FileUtil.uploadFile(avatar);
            channelDto.setAvatarPath(avatarPath);
        }
        Channel channel = channelMapper.mapToEntity(channelDto);
        return channelMapper.mapToDto(channelRepository.save(channel));
    }

    @Override
    public boolean existByLogin(String login) {
        Assert.isTrue(login != null && !login.isBlank(), "Login must not be null or blank");

        return channelRepository.findAll()
                .stream()
                .anyMatch(channel -> channel.getLogin().equals(login));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    @Override
    public ChannelDto updateChannel(ChannelDto channelDto) throws IOException {
        Channel channel = channelRepository.findById(channelDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Channel not found " + channelDto.getId()));

        if (channelDto.getAvatar() != null && !channelDto.getAvatar().isEmpty()) {
            if (channel.getAvatarPath() == null) {
                uploadChannelAvatar(channelDto);
            } else if (!channel.getAvatarPath().equals(channelDto.getAvatarPath())) {
                FileUtil.deleteFile("data/photos" + channel.getAvatarPath());
                uploadChannelAvatar(channelDto);
            }
        }
        channelMapper.updateChannelDto(channelDto, channel);
        return channelMapper.mapToDto(channel);
    }

    private static void uploadChannelAvatar(ChannelDto channelDto) throws IOException {
        String avatarPath = FileUtil.uploadFile(channelDto.getAvatar());
        channelDto.setAvatarPath(avatarPath);
    }

    @Override
    public ChannelDto findChannelById(Long id) {
        Channel channel = channelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Channel not found " + id));
        return channelMapper.mapToDto(channel);
    }

    @Override
    public Optional<ChannelDto> findAuthUserChannel() {
        return channelRepository.findChannelByUserId(authorizedUserService.getAuthorizedUserId())
                .map(channelMapper::mapToDto);
    }
}
