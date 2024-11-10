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

    /**
     * Identificador único del rol.
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    /**
     * Nombre del rol, por ejemplo, "ADMIN" o "USER".
     */
    String nombre

    // Relación de uno a muchos con UserRole
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<UserRole> userRoles
}
