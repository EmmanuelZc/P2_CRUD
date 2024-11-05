package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

@Controller
@RequestMapping("/updateUser")
class UpdateController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    UpdateController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/update")
    String mostrarFormularioBusqueda() {
        return "searchUpdate";  
    }

    @PostMapping("/findUser")
    String buscarUsuario(@RequestParam("idUsr") Long idUsr, Model model) {
        var usuarioOpt = userRepository.findById(idUsr);
        
        if (usuarioOpt.isPresent()) {
            model.addAttribute("usuario", usuarioOpt.get());
            return "update"; // Redirige a la vista de actualización
        } else {
            model.addAttribute("errorMessage", "Usuario no encontrado");
            return "searchUpdate";
        }
    }

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

            // Verifica y actualiza solo los campos no vacíos
            if (!StringUtils.isEmpty(nombre)) usuario.setNombre(nombre);
            if (!StringUtils.isEmpty(apellidoPaterno)) usuario.setApaterno(apellidoPaterno);
            if (!StringUtils.isEmpty(apellidoMaterno)) usuario.setAmaterno(apellidoMaterno);
            if (!StringUtils.isEmpty(fechaNacimiento)) usuario.setCumple(fechaNacimiento);
            if (!StringUtils.isEmpty(username)) usuario.setUsername(username);
            if (!StringUtils.isEmpty(password)) usuario.setPassword(passwordEncoder.encode(password));
            
            userRepository.save(usuario); 
            model.addAttribute("successMessage", "Información actualizada exitosamente.");
        } else {
            model.addAttribute("errorMessage", "Error al actualizar la información: Usuario no encontrado");
        }

        return "index";
    }
}
