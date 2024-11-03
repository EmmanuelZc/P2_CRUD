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
        try {
            User user = userRepository.findByUsername(usuario)
            def passwordEncoder = new BCryptPasswordEncoder()
            if (user != null && passwordEncoder.matches(password, user.password)) {
                // Excluir la contraseña del JSON devuelto
                user.password = null
                // Asegurarse de que los roles se incluyen en la respuesta
                return ResponseEntity.ok(user)
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized")
            }
        } catch (Exception e) {
            // Log del error para depuración
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor")
        }
    }

    @GetMapping("/auth/perfil/{username}")
    ResponseEntity<?> getUserProfile(@PathVariable("username") String username) {
        try {
            User user = userRepository.findByUsername(username)
            if (user != null) {
                // Excluir la contraseña del JSON devuelto
                user.password = null
                return ResponseEntity.ok(user)
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado")
            }
        } catch (Exception e) {
            // Log del error para depuración
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor")
        }
    }

        @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id)
                return ResponseEntity.ok("Usuario eliminado con exito")
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor")
        }
    }

    @PutMapping("/auth/update")
    ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        try {
            User user = userRepository.findById(updatedUser.id).orElse(null)

            if (user != null) {
                // Actualiza los campos del usuario existente con los datos nuevos (excluyendo la contraseña si no se desea actualizar)
                user.username = updatedUser.username ?: user.username
                user.email = updatedUser.email ?: user.email

                // Si se incluye una nueva contraseña en el objeto `updatedUser`, la ciframos antes de guardarla
                if (updatedUser.password) {
                    def passwordEncoder = new BCryptPasswordEncoder()
                    user.password = passwordEncoder.encode(updatedUser.password)
                }

                // Guarda los cambios en el repositorio
                userRepository.save(user)

                // Excluir la contraseña del JSON devuelto
                user.password = null
                return ResponseEntity.ok(user)
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado")
            }
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor")
        }
    }
}
