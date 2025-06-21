package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.LikeDto;
import kg.attractor.jobsearch.dto.mapper.LikeMapper;
import kg.attractor.jobsearch.model.Like;
import kg.attractor.jobsearch.repository.LikeRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.LikeService;
import kg.attractor.jobsearch.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final VideoService videoService;
    private final AuthorizedUserService authorizedUserService;
    private final LikeMapper likeMapper;

    @Transactional
    @Override
    public LikeDto like(LikeDto likeDto) {
        videoService.videoExistById(likeDto.getVideoId());

        Long authorizedUserId = authorizedUserService.getAuthorizedUserId();
        if (likeRepository.existLikeByUserIdAndVideId(authorizedUserId, likeDto.getVideoId())) {
            Like like = likeRepository.findLikeByVideoIdAndUserId(likeDto.getVideoId(), authorizedUserId)
                    .orElseThrow(() -> new NoSuchElementException("like not found"));
            like.setLike(true);
            return likeMapper.mapToDto(likeRepository.save(like));
        }

        likeDto.setUserId(authorizedUserService.getAuthorizedUserId());
        Like like = likeMapper.mapToEntity(likeDto);
        return likeMapper.mapToDto(likeRepository.save(like));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    @Override
    public void dislike(Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new NoSuchElementException("like not found by id " + likeId));

        Long userId = like.getUser().getUserId();
        if (!authorizedUserService.getAuthorizedUserId().equals(userId))
            throw new IllegalArgumentException("user is not own like");

        like.setLike(false);
    }

    @Override
    public LikeDto findLikeById(Long likeId) {
        Like like = likeRepository.findById(likeId)
                .orElseThrow(() -> new NoSuchElementException("like not found by id " + likeId));
        return likeMapper.mapToDto(like);
    }

    @Override
    public Optional<LikeDto> findAuthUserLikeByVideoId(Long videoId) {
        Assert.notNull(videoId, "videoId must not be null");

        Long authUserId = authorizedUserService.getAuthorizedUserId();
        return likeRepository.findLikeByVideoIdAndUserId(videoId, authUserId)
                .map(likeMapper::mapToDto);
    }

    @Override
    public List<LikeDto> findAllLikesByVideoId(Long videoId) {
        Assert.notNull(videoId, "videoId must not be null");
        List<Like> likes = likeRepository.findAllByVideoId(videoId);
        return likes.stream()
                .map(likeMapper::mapToDto)
                .toList();
    }
}
