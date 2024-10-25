package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Table

import jakarta.persistence.GenerationType
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany
import jakarta.persistence.ManyToMany
import jakarta.persistence.JoinTable
import jakarta.persistence.JoinColumn

import java.util.Set

@Entity
@Table(name = "usuarios") 
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    String nombre
    String apaterno
    String amaterno
    String cumple
    String username
    String password
    Boolean enabled 
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles", 
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    Set<Rol> roles
}
