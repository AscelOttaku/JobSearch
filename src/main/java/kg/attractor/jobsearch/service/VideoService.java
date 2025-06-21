package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VideoDto;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

public interface VideoService {
    VideoDto createVideo(VideoDto videoDto) throws IOException;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    VideoDto updateVideo(VideoDto videoDto) throws IOException;

    PageHolder<VideoDto> findVideosByChannelId(Long channelId, int page, int size);

    VideoDto findVideoById(Long id);

    void videoExistById(Long id);
}
