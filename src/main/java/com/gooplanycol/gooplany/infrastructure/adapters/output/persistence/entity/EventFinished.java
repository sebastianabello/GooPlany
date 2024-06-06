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
public class EventFinished {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_post_id")
    private EventPost eventPost;

    private LocalDateTime createAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_participants")
    private List<EventParticipant> eventParticipants;

}