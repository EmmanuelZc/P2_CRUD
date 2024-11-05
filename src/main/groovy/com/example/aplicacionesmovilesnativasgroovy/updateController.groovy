package com.example.aplicacionesmovilesnativasgroovy;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.thymeleaf.util.StringUtils;

/**
 * Controlador que maneja la actualización de información de usuario.
 * Permite la búsqueda de un usuario por ID y la actualización de sus datos personales.
 */
@Controller
@RequestMapping("/updateUser")
class UpdateController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor que inyecta el repositorio de usuarios y el codificador de contraseñas.
     *
     * @param userRepository Repositorio que maneja las operaciones CRUD de usuarios.
     * @param passwordEncoder Codificador de contraseñas para cifrar las nuevas contraseñas.
     */
    UpdateController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Muestra el formulario de búsqueda de usuario.
     *
     * @return Nombre de la vista para el formulario de búsqueda.
     */
    @GetMapping("/update")
    String mostrarFormularioBusqueda() {
        return "searchUpdate";  
    }

    /**
     * Busca un usuario por su ID y muestra sus datos si se encuentra en la base de datos.
     *
     * @param idUsr ID del usuario a buscar.
     * @param model Modelo para enviar atributos a la vista.
     * @return Nombre de la vista de actualización si el usuario se encuentra, o vista de búsqueda si no se encuentra.
     */
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

    /**
     * Actualiza la información de un usuario.
     * Solo actualiza los campos que no están vacíos.
     *
     * @param idUsr ID del usuario a actualizar.
     * @param nombre Nuevo nombre del usuario (opcional).
     * @param apellidoPaterno Nuevo apellido paterno del usuario (opcional).
     * @param apellidoMaterno Nuevo apellido materno del usuario (opcional).
     * @param fechaNacimiento Nueva fecha de nacimiento del usuario (opcional).
     * @param username Nuevo nombre de usuario (opcional).
     * @param password Nueva contraseña del usuario (opcional).
     * @param model Modelo para enviar mensajes de éxito o error a la vista.
     * @return Nombre de la vista de inicio después de la actualización.
     */
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
