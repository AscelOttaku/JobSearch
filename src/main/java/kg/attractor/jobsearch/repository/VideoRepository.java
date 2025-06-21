package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends JpaRepository<Video, Long> {
    Page<Video> findAllByChannelId(Long channelId, Pageable pageable);
}
