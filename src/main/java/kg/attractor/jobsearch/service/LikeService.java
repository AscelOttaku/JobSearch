package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.LikeDto;

import java.util.List;
import java.util.Optional;

public interface LikeService {
    LikeDto like(LikeDto likeDto);

    void dislike(Long likeId);

    LikeDto findLikeById(Long likeId);

    Optional<LikeDto> findAuthUserLikeByVideoId(Long videoId);

    List<LikeDto> findAllLikesByVideoId(Long videoId);
}
