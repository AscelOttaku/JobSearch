package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.GroupsMessages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsMessagesRepository extends JpaRepository<GroupsMessages, Long> {
}
