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
@Entity
@Table(name = "usuarios_roles")
class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    User user

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    Rol role
}
