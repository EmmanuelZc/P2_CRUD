package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.data.jpa.repository.JpaRepository

/**
 * Repositorio de usuarios que extiende JpaRepository para interactuar con la base de datos.
 */
interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Encuentra un usuario por su nombre de usuario.
     *
     * @param username el nombre de usuario
     * @return el usuario encontrado, o null si no existe
     */
    User findByUsername(String username)
}
