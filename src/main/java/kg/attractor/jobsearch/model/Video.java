package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "videos")
public class Video {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "channel_id", nullable = false, foreignKey = @ForeignKey(name = "fk_videos_channel_id"))
    private Channel channel;

    @Column(columnDefinition = "clob", name = "description")
    private String description;

    @Column(name = "video_url", nullable = false)
    private String videoUrl;

    @Column(name = "video_img_url", nullable = false)
    private String videoImgUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void setCreatedAd() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedAd() {
        updatedAt = LocalDateTime.now();
    }
}