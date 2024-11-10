package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam

/**
 * Controlador para gestionar el registro de nuevos usuarios.
 * Este controlador maneja las solicitudes de registro y la inserción de nuevos usuarios en la base de datos.
 */
@Controller
class RegisterController {

    private final UserRepository userRepository
    private final RolRepository rolRepository
    private final UserRoleRepository userRoleRepository
    private final PasswordEncoder passwordEncoder

    @Autowired
    RegisterController(UserRepository userRepository, RolRepository rolRepository, UserRoleRepository userRoleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository
        this.rolRepository = rolRepository
        this.userRoleRepository = userRoleRepository
        this.passwordEncoder = passwordEncoder
    }

    /**
     * Procesa la solicitud de registro de un nuevo usuario.
     * Recibe los datos del formulario de registro, encripta la contraseña y guarda los datos en la base de datos.
     *
     * @param nombre el nombre del usuario
     * @param apaterno el apellido paterno del usuario
     * @param amaterno el apellido materno del usuario
     * @param cumple la fecha de nacimiento del usuario
     * @param username el nombre de usuario
     * @param password la contraseña del usuario
     * @param model el modelo para pasar datos a la vista
     * @return el nombre de la vista de confirmación o de error
     */
    @PostMapping("/registro")
    String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apaterno") String apaterno,
            @RequestParam("amaterno") String amaterno,
            @RequestParam("cumple") String cumple,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

        try {
            // Crear y guardar el usuario
            User user = new User()
            user.setNombre(nombre)
            user.setApaterno(apaterno)
            user.setAmaterno(amaterno)
            user.setCumple(cumple)
            user.setUsername(username)
            user.setPassword(passwordEncoder.encode(password))
            user.setEnabled(true)

            userRepository.save(user)

            // Buscar o crear el rol "ROLE_USER"
            Rol rol = rolRepository.findByNombre("ROLE_USER")
            if (rol == null) {
                rol = new Rol()
                rol.setNombre("ROLE_USER")
                rolRepository.save(rol)
            }

            // Crear y guardar la relación UserRole
            UserRole userRole = new UserRole()
            userRole.setUser(user)
            userRole.setRole(rol)
            userRoleRepository.save(userRole)

            // Añadir datos del usuario al modelo
            model.addAttribute("nombre", nombre)
            model.addAttribute("apaterno", apaterno)
            model.addAttribute("amaterno", amaterno)
            model.addAttribute("cumple", cumple)
            model.addAttribute("username", username)

            return "index"

        } catch (Exception e) {
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage())
            return "registroError"
        }
    }
}
