package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "confirmation_token_company")
public class ConfirmationTokenCompanyEntity extends ConfirmationTokenEntity {
    @ManyToOne
    @JoinColumn(nullable = false, name = "company_id")
    private CompanyEntity company;
}
