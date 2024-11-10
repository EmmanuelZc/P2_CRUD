package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

/**
 * Repositorio de usuarios que extiende JpaRepository para interactuar con la base de datos.
 */
interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.username = :username")
    User findByUsernameWithRoles(@Param("username") String username)

     @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles ur LEFT JOIN FETCH ur.role")
    List<User> findAllWithRoles();
}