package com.gooplanycol.gooplany.domain.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventFinished {
    private Long id;
    private String concept;
    private List<EventPost> eventPosts;
    private LocalDateTime createAt;
    private EventParticipant eventParticipant;

    public List<EventPost> eventsPost(List<EventPost> list) {
        if (list != null) {
            return list.stream().map(eventPost -> new EventPost(
                    null,
                    eventPost.getTitle(),
                    eventPost.getDescription(),
                    eventPost.getEventCategory(),
                    eventPost.getTypeOfAudience(),
                    eventPost.getTypeOfPlace(),
                    eventPost.getIsFree(),
                    eventPost.getPrice(),
                    eventPost.getIsUnlimited(),
                    eventPost.getCapacity(),
                    eventPost.getStartAt(),
                    eventPost.getFinishAt(),
                    eventPost.getAddress()
            )).collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}
