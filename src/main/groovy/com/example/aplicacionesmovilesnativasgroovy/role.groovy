package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.*
import java.util.Set
import com.fasterxml.jackson.annotation.JsonManagedReference

@Entity
@Table(name = "roles")
class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id
    String nombre

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    Set<UserRole> userRoles
}
