package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "event_post_id")
    private EventPostEntity eventPost;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createAt;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "event_participants")
    private List<EventParticipantEntity> eventParticipants;


}