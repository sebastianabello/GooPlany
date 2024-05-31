package com.gooplanycol.gooplany.domain.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryCustomer {
    private Long id;
    private List<EventPost> eventPosts;
    private LocalDateTime updateAt;
}
