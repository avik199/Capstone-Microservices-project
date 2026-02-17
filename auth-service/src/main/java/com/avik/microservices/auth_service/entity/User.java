package com.avik.microservices.auth_service.entity;

import com.avik.microservices.auth_service.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String address;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;
}
