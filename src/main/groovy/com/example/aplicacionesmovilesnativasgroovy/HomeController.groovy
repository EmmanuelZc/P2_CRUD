package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.security.core.Authentication

/**
 * Controlador que gestiona las vistas principales de la aplicación.
 * Proporciona las páginas de inicio, registro y login.
 */
@Controller
class HomeController {

    /**
     * Muestra la página de inicio.
     * Si el usuario está autenticado, se añade su nombre de usuario al modelo;
     * de lo contrario, se muestra como "Guest".
     *
     * @param model el modelo para pasar datos a la vista
     * @param authentication la autenticación del usuario actual
     * @return el nombre de la vista "index" que se renderiza como la página de inicio
     */
    @GetMapping("/")
    def index(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated()
        model.addAttribute("isAuthenticated", isAuthenticated)
        model.addAttribute("username", isAuthenticated ? authentication.getName() : "Guest")
        return "index"  
    }

    /**
     * Muestra la página de registro.
     *
     * @param model el modelo para pasar datos a la vista
     * @return el nombre de la vista "register" que se renderiza como la página de registro
     */
    @GetMapping("/register")
    def register(Model model) {
        return "register"  // Se busca el archivo register.html en templates
    }

    /**
     * Muestra la página de inicio de sesión.
     *
     * @param model el modelo para pasar datos a la vista
     * @return el nombre de la vista "login" que se renderiza como la página de inicio de sesión
     */
    @GetMapping("/login")
    def login(Model model){
        return "login"
    }
}
