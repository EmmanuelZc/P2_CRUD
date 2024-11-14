package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.*
import com.fasterxml.jackson.annotation.JsonManagedReference
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // Relación uno a muchos con la tabla intermedia `usuarios_roles`
    @JsonManagedReference
    Set<UserRole> userRoles
}
