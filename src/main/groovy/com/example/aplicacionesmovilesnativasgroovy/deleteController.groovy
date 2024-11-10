package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.access.prepost.PreAuthorize
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Controller
@RequestMapping("/delete")
public class DeleteController {

    private static final Logger logger = LoggerFactory.getLogger(DeleteController.class);
    private final UserRepository userRepository;

    @Autowired
    public DeleteController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/userDelete")
    public String mostrarFormularioBusqueda() {
        return "delete";  
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/userDelete")
    public ResponseEntity<String> deleteUser(@RequestParam("id") Long id) {
        return userRepository.findById(id)
            .map(user -> {
                userRepository.delete(user);
                logger.info("Usuario con ID " + id + " eliminado con éxito");
                return ResponseEntity.ok("Usuario eliminado con éxito");
            })
            .orElseGet(() -> {
                logger.warn("Usuario con ID " + id + " no encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            });
    }
}
