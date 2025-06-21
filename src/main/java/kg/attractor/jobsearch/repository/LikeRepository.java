package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Like;
import kg.attractor.jobsearch.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    List<Like> video(Video video);

    @Query("select l from Like l where l.video.id = :videoId and l.user.userId = :userId")
    Optional<Like> findLikeByVideoIdAndUserId(Long videoId, Long userId);

    List<Like> findAllByVideoId(Long videoId);

    @Query("select count(l) > 0 from Like l where l.user.userId = :authorizedUserId and l.video.id = :videoId")
    boolean existLikeByUserIdAndVideId(Long authorizedUserId, Long videoId);

    @Modifying
    @Query("update Like l " +
            "set l.isLike = true " +
            "where l.user.userId = :userId and l.video.id = :videoId")
    void makeLikeByUserIdAndVideoId(Long userId, Long videoId);

    @Modifying
    @Query("update Like l " +
            "set l.isLike = false " +
            "where l.user.userId = :userId and l.video.id = :videoId")
    void makeDislikeByUserIdAndVideoId(Long userId, Long videoId);

    @Modifying
    @Query("update Like l " +
            "set l.isLike = false " +
            "where l.id = :id")
    void setIsLikeFalseByLikeId(Long id);
}
