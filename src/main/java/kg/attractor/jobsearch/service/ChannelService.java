package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ChannelDto;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.Optional;

public interface ChannelService {
    ChannelDto createChannel(ChannelDto channelDto) throws IOException;

    boolean existByLogin(String login);

    @Transactional(isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    ChannelDto updateChannel(ChannelDto channelDto) throws IOException;

    ChannelDto findChannelById(Long id);

    Optional<ChannelDto> findAuthUserChannel();

    void channelExistsById(Long id);
}
