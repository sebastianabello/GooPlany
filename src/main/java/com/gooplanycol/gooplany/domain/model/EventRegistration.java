package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {
    private Long id;
    private Profile profile;
    private EventPost eventPost;
    private LocalDateTime registeredAt;
}
