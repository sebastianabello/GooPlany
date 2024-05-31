package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_finished")
public class EventFinishedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String concept;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_posts")
    private List<EventPostEntity> eventPosts;

    private LocalDateTime createAt;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_participant_id")
    private List<EventParticipantEntity> eventParticipants;

}