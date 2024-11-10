package com.example.aplicacionesmovilesnativasgroovy

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.ui.Model

@Component
@ControllerAdvice
class GlobalControllerAdvice {

    @ModelAttribute
    void addAttributes(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication()
        if (authentication != null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal()
            model.addAttribute("isAuthenticated", true)
            model.addAttribute("username", user.getUsername()) 
        } else {
            model.addAttribute("isAuthenticated", false)
        }
    }
}
