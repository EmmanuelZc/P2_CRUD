package com.example.aplicacionesmovilesnativasgroovy
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.Authentication

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.List
import org.springframework.beans.factory.annotation.Autowired

@Controller
@RequestMapping("/users")
class UserReadController {

    private final UserRepository userRepository;

    @Autowired
    UserReadController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/list")
    String listarUsuarios(Model model) {
        List<User> usuarios = userRepository.findAllWithRoles();
        model.addAttribute("usuarios", usuarios);
        return "usersList";
    }

     // Nuevo método myInfo sin usar UserDetails
    @GetMapping("/myinfo")
    String verMiInfo(Model model) {
        // Obtener el objeto de autenticación actual
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        // Verificar si el usuario está autenticado
        if (authentication != null && authentication.isAuthenticated()) {
            String username = authentication.getName(); // Obtener el nombre de usuario desde Authentication
            User usuario = userRepository.findByUsernameWithRoles(username); // Buscar el usuario por su nombre de usuario
            
            if (usuario != null) {
                model.addAttribute("usuario", usuario); // Agregar el usuario al modelo
                return "myInfo"; // Retornar la vista myInfo
            }
        }
        
        // Si no está autenticado, redirigir al login
        return "redirect:/login"; 
    }
}