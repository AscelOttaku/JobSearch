package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users_locale")
public class UsersLocale {

    @Id
    @Column(name = "user_email", nullable = false, unique = true, updatable = false)
    private String userEmail;

    @Column(name = "locale", nullable = false)
    private String locale;
}
