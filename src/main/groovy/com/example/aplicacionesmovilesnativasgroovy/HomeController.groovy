package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.security.core.Authentication;

@Controller
class HomeController {

    @GetMapping("/")
    def index(Model model, Authentication authentication) {
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);
        model.addAttribute("username", isAuthenticated ? authentication.getName() : "Guest");
        return "index"  
    }

    @GetMapping("/register")
    def register(Model model) {
        return "register"  // Se busca el archivo register.html en templates
    }
    @GetMapping("/login")
    def login(Model model){
        return "login"
    }
}
