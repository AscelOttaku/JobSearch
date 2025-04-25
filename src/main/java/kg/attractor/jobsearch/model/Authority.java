package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "authority", nullable = false, unique = true)
    private String authorityName;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "authorities")
    private List<Role> roles;
}
