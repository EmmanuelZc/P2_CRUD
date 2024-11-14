package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Controller
@RequestMapping("/updateUser")
class UpdateController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsService userDetailsService;  // Inyectando UserDetailsService

    // Constructor que inyecta el repositorio de usuarios, codificador de contraseñas y el UserDetailsService
    UpdateController(UserRepository userRepository, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
    }

    // Método que muestra el formulario de actualización
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/update")
    String mostrarFormularioBusqueda(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
                          .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return "searchUpdate"; 
        } else {
            Long userId = obtenerIdUsuarioAutenticado();  // Llamada al método para obtener el ID del usuario autenticado
            if (userId != null) {
                model.addAttribute("usuario", userRepository.findById(userId).orElse(null));
            }
            return "update"; // Vista para el usuario común
        }
    }

    // Método para buscar un usuario por ID
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/findUser")
    String buscarUsuario(@RequestParam("idUsr") Long idUsr, Model model) {
        var usuarioOpt = userRepository.findById(idUsr);
        
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "update"; 
        } else {
            model.addAttribute("errorMessage", "Usuario no encontrado");
            return "searchUpdate";
        }
    }

    // Método para actualizar la información del usuario
    @PostMapping("/update")
    String actualizarUsuario(
            @RequestParam("idUsr") Long idUsr,
            @RequestParam("nombre") String nombre,
            @RequestParam("apellidoPaterno") String apellidoPaterno,
            @RequestParam("apellidoMaterno") String apellidoMaterno,
            @RequestParam("fechaNacimiento") String fechaNacimiento,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        var usuarioOpt = userRepository.findById(idUsr);

        if (usuarioOpt.isPresent()) {
            User usuario = usuarioOpt.get();

            // Actualización de campos con validación de no vacíos
            if (!StringUtils.isEmpty(nombre)) usuario.setNombre(nombre);
            if (!StringUtils.isEmpty(apellidoPaterno)) usuario.setApaterno(apellidoPaterno);
            if (!StringUtils.isEmpty(apellidoMaterno)) usuario.setAmaterno(apellidoMaterno);
            if (!StringUtils.isEmpty(fechaNacimiento)) usuario.setCumple(fechaNacimiento);
            if (!StringUtils.isEmpty(username)) usuario.setUsername(username);
            if (!StringUtils.isEmpty(password)) usuario.setPassword(passwordEncoder.encode(password));
            
            // Guardar el usuario actualizado
            userRepository.save(usuario); 
            model.addAttribute("successMessage", "Información actualizada exitosamente.");
        } else {
            model.addAttribute("errorMessage", "Error al actualizar la información: Usuario no encontrado");
        }

        return "index";  // Redirigir o mostrar la página principal después de la actualización
    }

    private Long obtenerIdUsuarioAutenticado() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userRepository.findByUsernameWithRoles(username);
        if (user != null) {
            return user.getId();
        }
    }
    return null;
}


}
