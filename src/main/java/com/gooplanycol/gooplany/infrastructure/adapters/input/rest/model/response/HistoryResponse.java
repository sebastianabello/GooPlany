package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.response;

import java.time.LocalDate;
import java.util.List;

public record HistoryResponse(
        Long id,
        List<EventPostResponse> eventPosts,
        LocalDate dateModification
) {
}
