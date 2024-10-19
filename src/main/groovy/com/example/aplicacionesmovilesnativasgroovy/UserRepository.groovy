package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username)
}
