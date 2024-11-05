package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Controlador REST para la gestión de usuarios.
 */
@RestController
@RequestMapping("/api")
class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Autentica al usuario con el nombre de usuario y contraseña proporcionados.
     *
     * @param usuario el nombre de usuario
     * @param password la contraseña
     * @return una respuesta que indica el resultado de la autenticación
     */
    @PostMapping("/auth")
    ResponseEntity<?> authenticateUser(@RequestParam("usuario") String usuario, @RequestParam("password") String password) {
        try {
            User user = userRepository.findByUsername(usuario);
            var passwordEncoder = new BCryptPasswordEncoder();
            if (user != null && passwordEncoder.matches(password, user.password)) {
                // Excluye la contraseña del JSON devuelto
                user.password = null;
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log del error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Obtiene el perfil del usuario especificado por el nombre de usuario.
     *
     * @param username el nombre de usuario
     * @return los detalles del perfil del usuario
     */
    @GetMapping("/auth/perfil/{username}")
    ResponseEntity<?> getUserProfile(@PathVariable("username") String username) {
        try {
            User user = userRepository.findByUsername(username);
            if (user != null) {
                // Excluye la contraseña del JSON devuelto
                user.password = null;
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log del error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Elimina un usuario dado su ID.
     *
     * @param id el ID del usuario a eliminar
     * @return un mensaje de éxito o error
     */
    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ResponseEntity.ok("Usuario eliminado con éxito");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log del error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }

    /**
     * Actualiza los detalles de un usuario existente.
     *
     * @param updatedUser el objeto usuario con los nuevos detalles
     * @return el usuario actualizado o un mensaje de error
     */
    @PutMapping("/auth/update")
    ResponseEntity<?> updateUser(@RequestBody User updatedUser) {
        try {
            User user = userRepository.findById(updatedUser.id).orElse(null);

            if (user != null) {
                // Actualiza los campos del usuario existente con los datos nuevos
                user.username = updatedUser.username != null ? updatedUser.username : user.username;
                user.email = updatedUser.email != null ? updatedUser.email : user.email;

                // Si se incluye una nueva contraseña en el objeto `updatedUser`, la ciframos antes de guardarla
                if (updatedUser.password != null) {
                    var passwordEncoder = new BCryptPasswordEncoder();
                    user.password = passwordEncoder.encode(updatedUser.password);
                }

                // Guarda los cambios en el repositorio
                userRepository.save(user);

                // Excluir la contraseña del JSON devuelto
                user.password = null;
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log del error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}
