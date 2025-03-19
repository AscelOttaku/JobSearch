package kg.attractor.jobsearch.dao;

import kg.attractor.jobsearch.model.User;

import java.util.Optional;

public interface Dao {
    Optional<User> findUserByEmail(String email);

    Optional<User> findUserByPhoneNumber(String phoneNumber);

    Optional<User> findUserByName(String name);
}
