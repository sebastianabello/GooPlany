package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.gooplanycol.gooplany.utils.StatusRegistrationEvent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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
    private StatusRegistrationEvent statusRegistrationEvent;

    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private ProfileEntity profile;

    @ManyToOne
    @JoinColumn(name = "event_post_id", referencedColumnName = "event_post_id")
    private EventPostEntity eventPost;



}
