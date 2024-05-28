package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import java.time.LocalDate;

public record HistoryRequest(
        LocalDate dateModification
) {
}
