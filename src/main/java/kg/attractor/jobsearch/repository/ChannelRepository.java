package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Long> {

    @Query("SELECT c FROM Channel c WHERE c.user.userId = :userId")
    Optional<Channel> findChannelByUserId(Long userId);
}
