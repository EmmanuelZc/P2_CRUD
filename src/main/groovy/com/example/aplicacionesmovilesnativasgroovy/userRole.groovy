package com.example.aplicacionesmovilesnativasgroovy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "usuarios_roles")
class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false) // Esta columna refiere a `usuarios.id`
    User user

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false) // Esta columna refiere a `roles.id`
    Rol role
}