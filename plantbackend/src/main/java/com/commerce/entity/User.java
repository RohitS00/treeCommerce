package com.commerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private String phoneNumber;
    private String role;

    @OneToOne(mappedBy = "user")//bidirectional mapping
    @JsonIgnore //adding this prevents could not get response error when loggin in
    // (helps in eliminating infinite loop of bidrectional relationship)
    private Provider provider;

    @OneToOne(mappedBy = "user")//bidirectional mapping
    @JsonIgnore
    private Consumer consumer;



}
