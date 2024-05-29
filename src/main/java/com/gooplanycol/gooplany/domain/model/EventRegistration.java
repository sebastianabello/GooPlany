package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.StatusEventPost;
import com.gooplanycol.gooplany.utils.StatusRegistrationEvent;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistration extends Utils {
    private Long id;
    private String statusRegistrationEvent;
    private LocalDateTime registeredAt;
    private Profile profile;
    private EventPost eventPost;

    public StatusRegistrationEvent StatusRegistrationEvent(String statusRegistrationEvent) {
        return StatusRegistrationEvent.valueOf(statusRegistrationEvent);
    }
}
