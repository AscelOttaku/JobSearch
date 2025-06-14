package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.nio.channels.FileChannel;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    @Query("select u.password from User u where u.userId = :userId")
    Optional<String> findUserPasswordByUserId(Long userId);

    Optional<User> findJobSeekerByEmail(String email);

    Optional<User> findEmployerByEmail(String email);

    List<User> findUserByName(String userName);

    @Query("select u from User u LEFT JOIN Resume AS r ON r.user.userId = u.userId " +
            "                JOIN RespondedApplication ra ON ra.resume.id = r.id " +
            "                JOIN Vacancy AS v ON v.id = ra.vacancy.id " +
            "                JOIN Role rl ON rl.id = u.role.id" +
            "                WHERE v.id = :vacancyId AND rl.roleName ilike 'JobSeeker'")
    List<User> findRespondedToVacancyUsersByVacancyId(Long vacancyId);

    @Modifying
    @Query("update User u set u.avatar = :avatar where u.email = :email")
    void updateAvatarByUserEmail(@Param(value = "avatar") String avatar, @Param(value = "email") String email);

    @Query("select u.userId from User u where u.email ilike :email")
    Optional<Long> findUserIdByEmail(String email);

    @Query("select u from User u " +
            "JOIN Vacancy v on v.user.userId = u.userId " +
            "where v.id = :vacancyId")
    Optional<User> findUserByVacancyId(Long vacancyId);

    @Query("select u from User u where u.role.roleName ilike :roleName")
    Page<User> findUsersByRoleRoleNameIgnoreCases(String roleName, Pageable pageable);

    @Query("select u.avatar from User u where u.userId = :userId")
    Optional<String> findUserAvatarById(Long userId);

    Optional<User> findUserByResetPasswordToken(String token);

    @Query("select u from User u " +
            "left join Resume r on r.user.userId = u.userId " +
            "left join RespondedApplication ra on ra.resume.id = r.id " +
            "where ra.id = :respondId")
    Optional<User> findUserByRespondId(Long respondId);

    @Query("select u from User u " +
            "left join Groups g on g.admin.userId = u.userId " +
            "where g.id = :groupId")
    Optional<User> findGroupsAdminByGroupId(Long groupId);
}
