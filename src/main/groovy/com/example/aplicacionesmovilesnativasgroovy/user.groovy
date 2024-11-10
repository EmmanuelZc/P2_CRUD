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

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonManagedReference;

/**
 * Entidad que representa un usuario en el sistema.
 */
@Entity
@Table(name = "usuarios") 
class User {

    /** Identificador único del usuario, generado automáticamente. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    Set<UserRole> userRoles;


}
