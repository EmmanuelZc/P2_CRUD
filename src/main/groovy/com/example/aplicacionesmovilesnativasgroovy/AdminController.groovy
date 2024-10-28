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
            users.forEach { it.password = null } // Excluir las contraseñas del JSON devuelto
            return ResponseEntity.ok(users)
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PostMapping(value = "/registro", consumes = "application/json", produces = "application/json")
    ResponseEntity<Void> registerUser(@RequestBody Map<String, Object> userMap) {
        try {
            // Mapear los datos del JSON a la entidad User
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
}
