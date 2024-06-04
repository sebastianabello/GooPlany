package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventFinished {
    private Long id;
    private String concept;
    private EventPost eventPost;
    private LocalDateTime createAt;
    private List<EventParticipant> eventParticipants;
}
