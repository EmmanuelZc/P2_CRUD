package com.example.aplicacionesmovilesnativasgroovy

import com.example.aplicacionesmovilesnativasgroovy.Rol
import com.example.aplicacionesmovilesnativasgroovy.User
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToOne
import jakarta.persistence.JoinColumn
import jakarta.persistence.Table

/**
 * Clase que representa la tabla intermedia "usuarios_roles", 
 * que relaciona los usuarios con los roles.
 */
@Entity
@Table(name = "usuarios_roles")
class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    /**
     * Relación muchos a uno con la entidad "User".
     * Cada rol está asociado con un usuario específico.
     */
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    User user

    /**
     * Relación muchos a uno con la entidad "Rol".
     * Cada rol está asociado con un usuario específico.
     */
    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    Rol role
}
