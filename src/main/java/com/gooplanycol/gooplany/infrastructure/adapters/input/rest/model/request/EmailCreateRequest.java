package com.gooplanycol.gooplany.infrastructure.adapters.input.rest.model.request;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmailCreateRequest {
    private String subject;
    private String text;
}
