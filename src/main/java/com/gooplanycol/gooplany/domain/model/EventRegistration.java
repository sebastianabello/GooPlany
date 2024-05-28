package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration {
    private Long id;
    private LocalDateTime registeredAt;
    private Profile profile;
    private EventPost eventPost;
}
