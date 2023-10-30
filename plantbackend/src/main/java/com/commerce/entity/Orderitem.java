package com.commerce.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Orderitem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    @ManyToOne
    @JoinColumn(name="order_id", nullable=false)
    private PurchaseOrder purchaseOrder;

    @ManyToOne
    @JoinColumn(name="plant_id", nullable=false)
    private Plant plant;
}
