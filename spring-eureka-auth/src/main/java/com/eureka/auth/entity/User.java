package com.eureka.auth.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    Long id;

    @NotNull
    String username;
    @NotNull
    @Column(name = "password_hash")
    String passwordHash;
    @Column(name = "display_name")
    String displayName;
    String mobile;
    String email;
    String address;
}
