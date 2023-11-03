package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Entity
@Data
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private double price;
    private String water;
    private String temperature;
    private String createdBy;
    private String updatedBy;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] pictureData;
    private int onHandValue; //for setting up inital stock value
    @Transient
    private MultipartFile plantImage;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToOne
    @JoinColumn(name="provider_id")
    @JsonIgnore
    private Provider provider;

    @OneToMany(mappedBy = "plant")
    @JsonIgnore
    private List<Orderitem> orderitemList;

    @OneToMany(mappedBy = "plant")
    @JsonIgnore
    private List<Cartitem> cartitemList;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="stock_id")
    private Stock stock;

    @Enumerated(EnumType.STRING)
    private Category category;

    @PrePersist //for audit fields
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
        createdBy = getProvider().getUser().getUsername();
        updatedBy = createdBy;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
        updatedBy = getProvider().getUser().getUsername();
    }



}
