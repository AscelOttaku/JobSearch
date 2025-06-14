package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Groups;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupsRepository extends JpaRepository<Groups, Long> {

    @Query("select count(g.id) from Groups g where g.admin.userId = :authorizedUserId")
    long countAllUserCreatedGroupsByUserId(Long authorizedUserId);
}
