package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    List<User> findUsersByName(String name);

    List<User> findRespondedToVacancyUsersByVacancy(Long vacancyId);

    Optional<User> findJobSeekerByEmail(String email);

    Optional<User> findEmployerByEmail(String email);
}
