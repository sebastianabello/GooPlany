package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Connection {
    private Profile profile1;
    private Profile profile2;
    private LocalDateTime createdAt;
}
