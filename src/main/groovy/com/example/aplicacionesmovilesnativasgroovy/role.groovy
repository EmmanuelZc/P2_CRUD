package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToMany
import jakarta.persistence.Table
import java.util.Set
import com.fasterxml.jackson.annotation.JsonBackReference


@Entity
@Table(name = "roles") 
class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String nombre

    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    Set<User> users
}