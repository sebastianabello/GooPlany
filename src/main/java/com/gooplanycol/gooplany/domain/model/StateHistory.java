package com.gooplanycol.gooplany.domain.model;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StateHistory {
    private UUID id;
    private User user;
    private UserState state;
    private LocalDateTime changedIn;
}
