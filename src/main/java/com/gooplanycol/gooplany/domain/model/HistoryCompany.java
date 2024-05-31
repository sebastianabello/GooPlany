package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryCompany {
    private Long id;
    private List<EventFinished> eventPosts;
    private LocalDateTime updateAt;
}
