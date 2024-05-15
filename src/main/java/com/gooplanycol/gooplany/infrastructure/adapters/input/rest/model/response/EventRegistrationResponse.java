package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.gooplanycol.gooplany.domain.model.EventPost;
import com.gooplanycol.gooplany.domain.model.Profile;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventRegistrationResponse {
    private Profile user;
    private EventPost eventPost;
    private LocalDateTime registeredAt;
}
