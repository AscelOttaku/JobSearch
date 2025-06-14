package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.GroupsMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GroupsMessagesRepository extends JpaRepository<GroupsMessages, Long> {
    List<GroupsMessages> findAllByGroupId(Long groupId);

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    @Modifying
    @Query("delete from GroupsMessages gm where gm.group.id = :groupId")
    void deleteAllMessagesByGroupId(Long groupId);
}
