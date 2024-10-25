package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@RestController
@RequestMapping("/api")
class UserController {

    private final UserRepository userRepository

    UserController(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @PostMapping("/auth")
    ResponseEntity<?> authenticateUser(@RequestParam("usuario") String usuario, @RequestParam("password") String password) {
        User user = userRepository.findByUsername(usuario)
        def passwordEncoder = new BCryptPasswordEncoder()
        if (user != null && passwordEncoder.matches(password, user.password)) {
            // Excluir la contraseña del JSON devuelto
            user.password = null
            return ResponseEntity.ok(user)
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
        }
    }
}
