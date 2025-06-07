package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.GroupsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsUsersRepository extends JpaRepository<GroupsUsers, Long> {
}
