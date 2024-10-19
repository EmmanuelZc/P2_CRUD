package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToMany
import java.util.Set

@Entity
class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String nombre
}
