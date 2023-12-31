package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false, updatable = false)
    private Date createdAt;

    private double cost;

    private String createdBy;

    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @ManyToOne(cascade = CascadeType.ALL) //with consumer //change this
    @JoinColumn(name = "consumer_id", referencedColumnName = "id")
    @JsonIgnore
    private Consumer consumer;

    @OneToMany(mappedBy = "purchaseOrder")
    @JsonIgnore
    private List<Orderitem> orderitemList;

    @OneToOne(mappedBy = "purchaseOrder")
    private Delivery delivery;

    @PrePersist //for audit fields
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        createdBy = getConsumer().getUser().getUsername();
        updatedBy = createdBy;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
        createdBy = getConsumer().getUser().getUsername();
    }

}
