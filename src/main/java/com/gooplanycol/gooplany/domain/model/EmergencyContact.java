package com.gooplanycol.gooplany.domain.model;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyContact {
    private UUID id;
    private String name;
    private String phoneNumber;
    private String relationship;
}
