package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    def index(Model model) {
        return "index"  // Se busca el archivo index.html en templates
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
