package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import java.util.List
import org.springframework.beans.factory.annotation.Autowired

@Controller
@RequestMapping("/users")
class UserReadController {

    private final UserRepository userRepository

    @Autowired
    UserReadController(UserRepository userRepository) {
        this.userRepository = userRepository
    }

    @GetMapping("/list")
    String listarUsuarios(Model model) {
        List<User> usuarios = userRepository.findAllWithRoles()
        model.addAttribute("usuarios", usuarios)
        return "usersList"
    }
}
