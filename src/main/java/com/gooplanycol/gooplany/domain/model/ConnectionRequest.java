package com.gooplanycol.gooplany.domain.model;

import lombok.*;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionRequest {
    private UUID id;
    private Profile senderProfile;
    private Profile receiverProfile;
    private ConnectionRequestStatus status;
}
