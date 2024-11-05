package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.ManyToMany
import java.util.Set
import com.fasterxml.jackson.annotation.JsonBackReference

/**
 * Entidad que representa un rol en el sistema.
 * Los roles definen los permisos o privilegios de los usuarios.
 */
@Entity
@Table(name = "roles")
class Rol {

    /**
     * Identificador único del rol.
     * Se genera automáticamente en la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id

    /**
     * Nombre del rol, por ejemplo, "ADMIN" o "USER".
     */
    String nombre

    /**
     * Conjunto de usuarios asociados a este rol.
     * La relación es bidireccional y se mapea desde la propiedad "roles" en la clase User.
     * La anotación @JsonBackReference evita la serialización recursiva en JSON.
     */
    @ManyToMany(mappedBy = "roles")
    @JsonBackReference
    Set<User> users
}
