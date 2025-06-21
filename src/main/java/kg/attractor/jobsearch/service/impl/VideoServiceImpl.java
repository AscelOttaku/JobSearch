package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VideoDto;
import kg.attractor.jobsearch.dto.mapper.VideoMapper;
import kg.attractor.jobsearch.dto.mapper.impl.PageHolderWrapper;
import kg.attractor.jobsearch.model.Video;
import kg.attractor.jobsearch.repository.VideoRepository;
import kg.attractor.jobsearch.service.ChannelService;
import kg.attractor.jobsearch.service.VideoService;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final ChannelService channelService;
    private final VideoMapper videoMapper;
    private final PageHolderWrapper pageHolderWrapper;

    @Override
    public VideoDto createVideo(VideoDto videoDto) throws IOException {
        Long channelId = videoDto.getChannelDto().getId();
        channelService.channelExistsById(channelId);
        if (!Objects.requireNonNull(videoDto.getVideoFile().getContentType()).startsWith("video/"))
            throw new IllegalArgumentException("Video file must be a video file");

        String videoPath = FileUtil.uploadFile("data/videos", videoDto.getVideoFile());
        videoDto.setVideoUrl(videoPath);

        String videoImgUrl = FileUtil.uploadFile(videoDto.getVideoImage());
        videoDto.setVideoImgUrl(videoImgUrl);

        Video video = videoMapper.mapToEntity(videoDto);
        return videoMapper.mapToDto(videoRepository.save(video));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public VideoDto updateVideo(VideoDto videoDto) throws IOException {
        Video video = videoRepository.findById(videoDto.getId())
                .orElseThrow(() -> new NoSuchElementException("Video not found " + videoDto.getId()));

        if (videoDto.getVideoFile() != null && !videoDto.getVideoFile().isEmpty()) {
            FileUtil.deleteFile("data/videos" + video.getVideoUrl());
            String videoPath = FileUtil.uploadFile("data/videos", videoDto.getVideoFile());
            videoDto.setVideoUrl(videoPath);
        }

        if (videoDto.getVideoImage() != null && !videoDto.getVideoImage().isEmpty()) {
            FileUtil.deleteFile("data/photos" + video.getVideoImgUrl());
            String videoImgUrl = FileUtil.uploadFile(videoDto.getVideoImage());
            videoDto.setVideoImgUrl(videoImgUrl);
        }

        videoMapper.updateEntity(videoDto, video);
        return videoMapper.mapToDto(video);
    }

    @Override
    public PageHolder<VideoDto> findVideosByChannelId(Long channelId, int page, int size) {
        channelService.channelExistsById(channelId);

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<VideoDto> videosPage = videoRepository.findAllByChannelId(channelId, pageable)
                .map(videoMapper::mapToDto);
        return pageHolderWrapper.wrapPageHolder(videosPage);
    }

    @Override
    public VideoDto findVideoById(Long id) {
        VideoDto videoDto = videoRepository.findById(id)
                .map(videoMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Video not found " + id));

        videoDto.setMediaType(FileUtil.defineFileTypeVideo(videoDto.getVideoUrl()));
        return videoDto;
    }

    @Override
    public void videoExistById(Long id) {
        if (!videoRepository.existsById(id))
            throw new NoSuchElementException("video not exist by id " + id);
    }
}
