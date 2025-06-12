package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.GroupsUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsUsersRepository extends JpaRepository<GroupsUsers, Long> {

    @Query("select exists (select ug from GroupsUsers ug where ug.group.id = :groupId and ug.user.userId = :userId)")
    boolean existsByGroupIdAndUserId(Long groupId, Long userId);

    @Query("select count(gu.id) from GroupsUsers gu " +
            "join Groups g on gu.group.id = g.id " +
            "where g.id = :groupId")
    Long findMembersCountByGroupId(Long groupId);
}
