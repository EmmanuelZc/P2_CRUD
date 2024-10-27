package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
@RequestMapping("/api/auth")
class AdminController {

    private final UserRepository userRepository

    AdminController(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @PostMapping("/admin")
    ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll()
            users.forEach(user -> user.password = null) // Excluir la contraseña del JSON devuelto
            return ResponseEntity.ok(users)
        } catch (Exception e) {
            // Log del error para depuración
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }
}
