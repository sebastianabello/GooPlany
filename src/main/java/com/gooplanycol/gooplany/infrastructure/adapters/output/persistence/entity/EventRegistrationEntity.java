package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_registration")
public class EventRegistrationEntity {
    @Id
    @Column(name = "event_registration_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "event_post_id", referencedColumnName = "event_post_id")
    private EventPostEntity eventPost;

    @CreationTimestamp
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

}
