package com.gooplanycol.gooplany.domain.model;

import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import lombok.*;


import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventParticipant {
    private Long id;
    private StatusEventParticipant statusRegistration;
    private LocalDateTime registeredAt;
    private Customer customer;
    private CreditCard creditCard;
}
