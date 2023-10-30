package com.commerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Cartitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="cart_id", nullable=false)
    private Cart cart;
    @ManyToOne
    @JoinColumn(name="plant_id", nullable=false)
    private Plant plant;

}
