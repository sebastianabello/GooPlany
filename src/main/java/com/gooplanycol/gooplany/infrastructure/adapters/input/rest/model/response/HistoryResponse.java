package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HistoryResponse {
    private Long id;
    private EventPostResponse eventPosts;
    private List<EventParticipantResponse> eventParticipants;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateModification;
}