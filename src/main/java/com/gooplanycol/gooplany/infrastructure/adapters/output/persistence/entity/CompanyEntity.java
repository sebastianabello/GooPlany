package com.gooplanycol.gooplany.infrastructure.adapters.output.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "companies")
public class CompanyEntity {

    @Id
    @Column(name = "company_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @OneToOne
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressEntity address;

}
