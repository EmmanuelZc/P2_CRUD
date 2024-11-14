package com.example.aplicacionesmovilesnativasgroovy

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Table;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
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

    String nombre
    String apaterno
    String amaterno
    String cumple
    String username
    String password
    Boolean enabled

    @OneToMany(mappedBy = "user")  // Relación uno a muchos con la tabla intermedia `usuarios_roles`
    Set<UserRole> userRoles
}
