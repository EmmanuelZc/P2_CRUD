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
    ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userRepository.findAllWithRoles()
            List<Map<String, Object>> response = users.stream().map { user ->
                Map<String, Object> userMap = new HashMap<>()
                userMap.put("id", user.id)
                userMap.put("nombre", user.nombre)
                userMap.put("apaterno", user.apaterno)
                userMap.put("amaterno", user.amaterno)
                userMap.put("cumple", user.cumple)
                userMap.put("username", user.username)
                userMap.put("enabled", user.enabled)
                userMap.put("roles", user.userRoles.stream().map { userRole ->
                    Map<String, Object> roleMap = new HashMap<>()
                    roleMap.put("id", userRole.role.id)
                    roleMap.put("nombre", userRole.role.nombre)
                    roleMap
                }.toList())
                userMap
            }.toList()
            return ResponseEntity.ok(response)
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
            user.userRoles = new HashSet<>()
            userMap.get("roles").each { rolMap ->
                UserRole userRole = new UserRole()
                Rol rol = new Rol()
                rol.id = (rolMap.get("id") as Number).longValue()
                rol.nombre = rolMap.get("nombre").toString()
                userRole.user = user
                userRole.role = rol
                user.userRoles.add(userRole)
            }
            userRepository.save(user)
            return ResponseEntity.status(HttpStatus.CREATED).build()
        } catch (Exception e) {
            e.printStackTrace()
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    ResponseEntity<?> updateUser(@RequestBody Map<String, Object> userMap) {
        try {
            User existingUser = userRepository.findByUsername(userMap.get("username").toString())
            if (existingUser != null) {
                existingUser.nombre = userMap.get("nombre").toString()
                existingUser.apaterno = userMap.get("apaterno").toString()
                existingUser.amaterno = userMap.get("amaterno").toString()
                existingUser.cumple = userMap.get("cumple").toString()
                existingUser.password = userMap.get("password").toString() // Ya viene encriptada
                existingUser.enabled = userMap.get("enabled") as Boolean

                // Actualizar los roles
                existingUser.userRoles.clear()
                userMap.get("roles").each { rolMap ->
                    UserRole userRole = new UserRole()
                    Rol rol = new Rol()
                    rol.id = (rolMap.get("id") as Number).longValue()
                    rol.nombre = rolMap.get("nombre").toString()
                    userRole.user = existingUser
                    userRole.role = rol
                    existingUser.userRoles.add(userRole)
                }

                userRepository.save(existingUser)

                // Preparar respuesta con los roles en el formato requerido
                Map<String, Object> response = new HashMap<>()
                response.put("id", existingUser.id)
                response.put("nombre", existingUser.nombre)
                response.put("apaterno", existingUser.apaterno)
                response.put("amaterno", existingUser.amaterno)
                response.put("cumple", existingUser.cumple)
                response.put("username", existingUser.username)
                response.put("enabled", existingUser.enabled)
                response.put("roles", existingUser.userRoles.stream().map { userRole ->
                    Map<String, Object> roleMap = new HashMap<>()
                    roleMap.put("id", userRole.role.id)
                    roleMap.put("nombre", userRole.role.nombre)
                    roleMap
                }.toList())

                return ResponseEntity.ok(response)
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
            User user = userRepository.findByUsername(username)
            if (user != null) {
                userRepository.delete(user)
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
