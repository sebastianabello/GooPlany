package com.gooplanycol.gooplany.domain.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Media {
    private Long id;
    private String fileName;
    private String contentType;
    private String url;
    private Long size;
    private EventPost eventPost;
    private User user;
}
