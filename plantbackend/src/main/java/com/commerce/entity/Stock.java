package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int onHand;
    private int sold;
    private int inCart;
    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    private List<Plant> plantList;
}
