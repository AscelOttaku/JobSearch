package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "resumes")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    private LocalDateTime created;
    private LocalDateTime updated;

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<WorkExperienceInfo> workExperienceInfos = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<EducationInfo> educationInfos = new ArrayList<>();

    @OneToMany(mappedBy = "resume", fetch = FetchType.LAZY)
    private List<ContactInfo> contactInfos = new ArrayList<>();
}
