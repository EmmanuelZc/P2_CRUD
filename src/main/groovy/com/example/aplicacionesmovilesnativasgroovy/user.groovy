package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Table
import jakarta.persistence.CascadeType
import jakarta.persistence.GenerationType
import jakarta.persistence.FetchType
import jakarta.persistence.Table
import jakarta.persistence.ManyToMany
import jakarta.persistence.OneToMany
import jakarta.persistence.JoinTable
import jakarta.persistence.JoinColumn
import java.util.Set
import com.fasterxml.jackson.annotation.JsonManagedReference


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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<UserRole> userRoles;



}
