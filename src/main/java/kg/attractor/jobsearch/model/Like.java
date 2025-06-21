package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "likes", uniqueConstraints = {
        @UniqueConstraint(name = "uq_likes_user_channel", columnNames = {"user_id", "video_id"})
})
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @Column(name = "is_like", nullable = false)
    private boolean isLike;
}