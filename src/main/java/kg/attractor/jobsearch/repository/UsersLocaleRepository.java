package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.UsersLocale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersLocaleRepository extends JpaRepository<UsersLocale, String> {
    Optional<UsersLocale> findUsersLocaleByUserEmail(String userEmail);
}
