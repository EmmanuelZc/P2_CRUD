package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonBackReference

@Entity
@Table(name = "usuarios_roles")
class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false) // Esta columna refiere a `usuarios.id`
    @JsonBackReference
    User user

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rol_id", nullable = false) // Esta columna refiere a `roles.id`
    @JsonBackReference
    Rol role
}
