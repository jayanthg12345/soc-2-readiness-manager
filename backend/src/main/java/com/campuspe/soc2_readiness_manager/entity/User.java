package com.campuspe.soc2_readiness_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private UUID id;

    @Column(unique = true)
    private String email;

    private String password;

    private String role; // ADMIN, MANAGER, VIEWER
}