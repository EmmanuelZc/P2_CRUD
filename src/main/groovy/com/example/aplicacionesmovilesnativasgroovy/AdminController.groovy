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
