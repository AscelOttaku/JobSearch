package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = "select a.* from AUTHORITIES a " +
            "join ROLES_AUTHORITIES ra on ra.AUTHORITY_ID = a.ID " +
            "where ra.ROLE_ID = :roleId", nativeQuery = true)
    List<Authority> findAllAuthoritiesByRoleId(Long roleId);
}
