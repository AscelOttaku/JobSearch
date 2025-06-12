package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.GroupsMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupsMessagesRepository extends JpaRepository<GroupsMessages, Long> {
    List<GroupsMessages> findAllByGroupId(Long groupId);
}
