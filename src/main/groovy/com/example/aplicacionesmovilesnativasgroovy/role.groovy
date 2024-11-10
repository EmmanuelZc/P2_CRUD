package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Table
import jakarta.persistence.OneToMany
import jakarta.persistence.CascadeType
import java.util.Set

@Entity
@Table(name = "roles")
class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String nombre

    // Relación de uno a muchos con UserRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserRole> userRoles
}
