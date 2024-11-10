package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

/**
 * Controlador para la gestión de usuarios administrativos en la API de autenticación.
 * Proporciona endpoints para obtener todos los usuarios, registrar nuevos usuarios,
 * actualizar usuarios existentes y eliminar usuarios por nombre de usuario.
 */
@RestController
@RequestMapping("/api/auth")
class AdminController {

    private final UserRepository userRepository

    /**
     * Constructor de AdminController.
     *
     * @param userRepository el repositorio de usuarios que permite realizar operaciones CRUD en la base de datos.
     */
    AdminController(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    /**
     * Obtiene todos los usuarios registrados.
     *
     * @return ResponseEntity con la lista de usuarios y estado OK si la operación es exitosa,
     *         o un estado de error interno si ocurre una excepción.
     */
    @PostMapping("/admin")
    ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll()
            users.forEach { it.password = null } // Excluir las contraseñas del JSON devuelto
            return ResponseEntity.ok(users)
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    /**
     * Registra un nuevo usuario con la información proporcionada.
     *
     * @param userMap mapa de los datos del usuario, incluyendo nombre, apellidos, fecha de nacimiento,
     *                nombre de usuario, contraseña, estado habilitado y roles.
     * @return ResponseEntity con el estado de creado si la operación es exitosa,
     *         o un estado de error interno si ocurre una excepción.
     */
    @PostMapping(value = "/registro", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> registerUser(@RequestBody Map<String, Object> userMap) {
        try {
            User user = new User()
            user.nombre = userMap.get("nombre").toString()
            user.apaterno = userMap.get("apaterno").toString()
            user.amaterno = userMap.get("amaterno").toString()
            user.cumple = userMap.get("cumple").toString()
            user.username = userMap.get("username").toString()
            user.password = userMap.get("password").toString()
            user.enabled = userMap.get("enabled") as Boolean
            user.roles = new HashSet<>()
            userMap.get("roles").each { rolMap ->
                Rol rol = new Rol()
                rol.id = (rolMap.get("id") as Number).longValue()
                rol.nombre = rolMap.get("nombre").toString()
                user.roles.add(rol)
            }
            userRepository.save(user)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Actualiza un usuario existente con la información proporcionada.
     *
     * @param userMap mapa de los datos del usuario que incluyen nombre, apellidos,
     *                fecha de nacimiento, nombre de usuario, contraseña, estado habilitado y roles.
     * @return ResponseEntity con el estado OK si el usuario se actualiza correctamente,
     *         estado NOT FOUND si el usuario no existe, o un estado de error interno si ocurre una excepción.
     */
    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> updateUser(@RequestBody Map<String, Object> userMap) {
        try {
            User existingUser = userRepository.findByUsername(userMap.get("username").toString())
            if (existingUser != null) {
                existingUser.nombre = userMap.get("nombre").toString()
                existingUser.apaterno = userMap.get("apaterno").toString()
                existingUser.amaterno = userMap.get("amaterno").toString()
                existingUser.cumple = userMap.get("cumple").toString()
                existingUser.password = userMap.get("password").toString() // Ya viene encriptada
                existingUser.enabled = userMap.get("enabled") as Boolean
                existingUser.roles = new HashSet<>()
                userMap.get("roles").each { rolMap ->
                    Rol rol = new Rol()
                    rol.id = (rolMap.get("id") as Number).longValue()
                    rol.nombre = rolMap.get("nombre").toString()
                    existingUser.roles.add(rol)
                }
                userRepository.save(existingUser)
                return ResponseEntity.status(HttpStatus.OK).build()
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    /**
     * Elimina un usuario existente por su nombre de usuario.
     *
     * @param username el nombre de usuario del usuario a eliminar.
     * @return ResponseEntity con el estado NO CONTENT si el usuario se elimina correctamente,
     *         estado NOT FOUND si el usuario no existe, o un estado de error interno si ocurre una excepción.
     */
    @DeleteMapping(value = "/delete/{username}", produces = "application/json")
    ResponseEntity<Void> deleteUser(@PathVariable("username") String username) {
        try {
            User existingUser = userRepository.findByUsername(username)
            if (existingUser != null) {
                userRepository.delete(existingUser)
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
            }
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
}
