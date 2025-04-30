package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void save_user_test() {
        User user = new User();
        user.setUserId(1L);
        user.setName("test");
        user.setSurname("test");
        user.setEmail("test@test.com");
        user.setPassword("test");
        user.setPhoneNumber("test");
        user.setRole(new Role());
        user.setEnabled(true);

        User save = userRepository.save(user);

        Assertions.assertThat(save).isNotNull();
        Assertions.assertThat(save.getUserId()).isGreaterThan(0);
        Assertions.assertThat(save.getUserId()).isEqualTo(user.getUserId());
    }
}
