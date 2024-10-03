package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {

    @GetMapping("/")
    def index(Model model) {
        return "index"  
    }
}
