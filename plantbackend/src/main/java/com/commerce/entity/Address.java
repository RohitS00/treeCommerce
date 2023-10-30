package com.commerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String buildingNumber;
    private String streetName;
    private String city;
    private String zipCode;
    @ManyToOne
    @JoinColumn(name="country_id", nullable=false) //with country
    private Country country;

    @OneToOne(mappedBy = "address") //non owning side with provider
    private Provider provider;

    @OneToOne(mappedBy = "address") //with consumer
    private Consumer consumer;

}
