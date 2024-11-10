package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.data.jpa.repository.JpaRepository

interface UserRoleRepository extends JpaRepository<UserRole, Long> {
  
}
