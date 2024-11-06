package com.example.aplicacionesmovilesnativasgroovy;

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

    /** Nombre del usuario. */
    String nombre;

    /** Apellido paterno del usuario. */
    String apaterno;

    /** Apellido materno del usuario. */
    String amaterno;

    /** Fecha de nacimiento del usuario, almacenada como cadena de texto. */
    String cumple;

    /** Nombre de usuario utilizado para autenticación. */
    String username;

    /** Contraseña cifrada del usuario. */
    String password;

    /** Indica si la cuenta del usuario está habilitada o no. */
    Boolean enabled;

    /**
     * Roles asociados al usuario.
     * La relación es de muchos a muchos, y se utiliza `EAGER` para cargar los roles junto con el usuario.
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "usuarios_roles",
        joinColumns = @JoinColumn(name = "usuario_id"),
        inverseJoinColumns = @JoinColumn(name = "rol_id")
    )
    @JsonManagedReference
    Set<Rol> roles;

    // Getters y Setters, si es necesario, pueden agregarse aquí
}
