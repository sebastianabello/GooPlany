package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import jakarta.persistence.*;
import lombok.*;

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

    @Enumerated(EnumType.STRING)
    private StatusEventParticipant statusEventParticipant;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "user_id")
    private ProfileEntity profileId;

    @ManyToOne
    @JoinColumn(name = "event_post_id", referencedColumnName = "event_post_id")
    private EventPostEntity eventPostId;


}
