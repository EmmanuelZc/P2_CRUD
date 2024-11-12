package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "usuarios")
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    @Column(name = "nombre", nullable = false)
    String nombre

    @Column(name = "apaterno", nullable = false)
    String apaterno

    @Column(name = "amaterno", nullable = false)
    String amaterno

    @Column(name = "cumple", nullable = false)
    String cumple

    @Column(name = "username", unique = true, nullable = false)
    String username

    @Column(name = "password", nullable = false)
    String password

    @Column(name = "enabled", nullable = false)
    Boolean enabled

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<UserRole> userRoles
}