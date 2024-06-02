package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gooplanycol.gooplany.utils.StatusEventParticipant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "event_participant")
public class EventParticipantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusEventParticipant statusRegistration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registeredAt;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;

    @ManyToOne
    @JoinColumn(name = "credit_card_id")
    private CreditCardEntity creditCard;
}
