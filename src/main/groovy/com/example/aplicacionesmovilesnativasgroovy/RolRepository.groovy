package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.data.jpa.repository.JpaRepository

interface RolRepository extends JpaRepository<Rol, Long> {
    Rol findByNombre(String nombre)
}
