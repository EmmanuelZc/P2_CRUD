package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import com.example.utils.DatabaseUtils
import groovy.sql.Sql

/**
 * Controlador para gestionar el registro de nuevos usuarios.
 * Este controlador maneja las solicitudes de registro y la inserción de nuevos usuarios en la base de datos.
 */
@Controller
public class RegisterController {

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
    public String registrarUsuario(
            @RequestParam("nombre") String nombre,
            @RequestParam("apaterno") String apaterno,
            @RequestParam("amaterno") String amaterno,
            @RequestParam("cumple") String cumple,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            Model model) {

            Sql sql = null
            def passwordEncoder = new BCryptPasswordEncoder()
            def hashedPassword = passwordEncoder.encode(password) 
           
        try {
            // Obtener la instancia de conexión a la base de datos
            sql = DatabaseUtils.getSqlInstance()

            // Consulta SQL para insertar el nuevo usuario en la base de datos
            String insertQuery = "INSERT INTO usuarios (nombre, apaterno, amaterno, cumple, username, password) VALUES (?, ?, ?, ?, ?, ?)"

            // Ejecutar la consulta con los parámetros recibidos
            sql.execute(insertQuery, nombre, apaterno, amaterno, cumple, username, hashedPassword)

            // Añadir los datos del usuario al modelo para enviarlos a la vista
            model.addAttribute("nombre", nombre)
            model.addAttribute("apaterno", apaterno)
            model.addAttribute("amaterno", amaterno)
            model.addAttribute("cumple", cumple)
            model.addAttribute("username", username)

            // Redirigir a una vista de confirmación
            return "index"

        } catch (Exception e) {
            // En caso de error, añadir un mensaje al modelo
            model.addAttribute("error", "Error al registrar el usuario: " + e.getMessage())
            return "registroError"
        } finally {
            if (sql != null) {
                sql.close() // Cerrar la conexión
            }
        }
    }
}
