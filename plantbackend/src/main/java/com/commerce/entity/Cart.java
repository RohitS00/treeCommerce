package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "cart")
    @JsonIgnore
    private List<Cartitem> cartitemList;

    @OneToOne(cascade = CascadeType.ALL) //with consumer
    @JoinColumn(name = "consumer_id", referencedColumnName = "id")
    private Consumer consumer;

}
