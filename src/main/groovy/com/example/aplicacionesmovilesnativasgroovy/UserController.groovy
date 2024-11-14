package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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
            User user = userRepository.findByUsernameWithRoles(usuario);
            var passwordEncoder = new BCryptPasswordEncoder();
            if (user != null && passwordEncoder.matches(password, user.password)) {
                // Preparar respuesta
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.id);
                response.put("nombre", user.nombre);
                response.put("apaterno", user.apaterno);
                response.put("amaterno", user.amaterno);
                response.put("cumple", user.cumple);
                response.put("username", user.username);
                response.put("enabled", user.enabled);
                response.put("roles", user.userRoles.stream().map(userRole -> {
                    Map<String, Object> roleMap = new HashMap<>();
                    roleMap.put("id", userRole.role.id);
                    roleMap.put("nombre", userRole.role.nombre);
                    return roleMap;
                }).collect(Collectors.toList()));

                // Excluir la contraseña del JSON devuelto
                user.password = null;
                return ResponseEntity.ok(response);
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
            User user = userRepository.findByUsernameWithRoles(username);
            if (user != null) {
                // Preparar respuesta
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.id);
                response.put("nombre", user.nombre);
                response.put("apaterno", user.apaterno);
                response.put("amaterno", user.amaterno);
                response.put("cumple", user.cumple);
                response.put("username", user.username);
                response.put("enabled", user.enabled);
                response.put("roles", user.userRoles.stream().map(userRole -> {
                    Map<String, Object> roleMap = new HashMap<>();
                    roleMap.put("id", userRole.role.id);
                    roleMap.put("nombre", userRole.role.nombre);
                    return roleMap;
                }).collect(Collectors.toList()));

                // Excluir la contraseña del JSON devuelto
                user.password = null;
                return ResponseEntity.ok(response);
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
                user.nombre = updatedUser.nombre != null ? updatedUser.nombre : user.nombre;
                user.apaterno = updatedUser.apaterno != null ? updatedUser.apaterno : user.apaterno;
                user.amaterno = updatedUser.amaterno != null ? updatedUser.amaterno : user.amaterno;
                user.cumple = updatedUser.cumple != null ? updatedUser.cumple : user.cumple;
                user.enabled = updatedUser.enabled != null ? updatedUser.enabled : user.enabled;

                // Si se incluye una nueva contraseña en el objeto `updatedUser`, la ciframos antes de guardarla
                if (updatedUser.password != null) {
                    var passwordEncoder = new BCryptPasswordEncoder();
                    user.password = passwordEncoder.encode(updatedUser.password);
                }

                // Actualiza los roles del usuario
                if (updatedUser.userRoles != null) {
                    user.userRoles.clear();
                    user.userRoles.addAll(updatedUser.userRoles);
                }

                // Guarda los cambios en el repositorio
                userRepository.save(user);

                // Preparar respuesta con los roles en el formato requerido
                Map<String, Object> response = new HashMap<>();
                response.put("id", user.id);
                response.put("nombre", user.nombre);
                response.put("apaterno", user.apaterno);
                response.put("amaterno", user.amaterno);
                response.put("cumple", user.cumple);
                response.put("username", user.username);
                response.put("enabled", user.enabled);
                response.put("roles", user.userRoles.stream().map(userRole -> {
                    Map<String, Object> roleMap = new HashMap<>();
                    roleMap.put("id", userRole.role.id);
                    roleMap.put("nombre", userRole.role.nombre);
                    return roleMap;
                }).toList());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log del error para depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error interno del servidor");
        }
    }
}