package kg.attractor.jobsearch.model;

import jakarta.persistence.*;
import kg.attractor.jobsearch.enums.MessageType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "responded_application_id", nullable = false)
    private RespondedApplication respondedApplication;

    @Column(name = "content", columnDefinition = "clob", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "message_owner_id", nullable = false)
    private User owner;

    @Column(name = "time")
    private LocalDateTime time;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "message_type", nullable = false)
    private MessageType messageType;
}
